package com.love.iLove.security.voter;

import com.love.iLove.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * springsecurity 原始鉴权流程如下：
 *      FilterSecurityInterceptor(在进行下一步之间进行权限校验) --> AbstractSecurityInterceptor (获取请求该url所需要的权限和用户所拥有的权限)--> AffirmativeBased(判断用户是否拥有请求该url权限，一种判别机制) --> WebExpressionVoter(这里才正式判断权限)
 * 方式有2种：
 *     1.在复写的configure里直接定义
 *          .antMatchers("your match rule").authenticated()
 *          .antMatchers("your match rule").hasRole("ADMIN") //使用时权限会自动加前缀ROLE_ADMIN
 *     2.在方法上加入@Secured("ROLE_ADMIN")注解，但是需先在具体的配置里开启此类配置
 *          @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
 * 上面两种方法最终都会生成一个FilterSecurityInterceptor实例, 放在filter过滤链底部
 *
 * 下面说一下把原始的硬编码鉴权流程替换成从数据中动态读取的思路和具体实现方法：
 *      方法一：自定义SecurityMetadataSource
 *          在AbstractSecurityInterceptor中，通过
 *              Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource().getAttributes(object);
 *              获取用户访问路径的所需权限。该方法是由DefaultFilterInvocationSecurityMetadataSource类提供的，获取的是configure里面配置的访问该url所需要的权限。
 *              而DefaultFilterInvocationSecurityMetadataSource是SecurityMetadataSource的默认实现类。该类存储了一个配置的角色——路径对应关系（requestMap），用户发起请求时，会从存储的对应关系中，按照请求路径查找对应的权限
 *              然后传递给AbstractSecurityInterceptor。
 *          实现方式：
 *              1. 新建实现类实现FilterInvocationSecurityMetadataSource
 *              2. 仿照DefaultFilterInvocationSecurityMetadataSource类实现getAttributes(Object object)，getAllConfigAttributes和supports(Class<?> clazz)方法
 *                  注意：supports(Class<?> clazz) 必须返回true，否者不会进入getAttributes(Object object)方法
 *                      getAttributes(Object object)方法重的object为FilterInvocation，而FilterInvocation类是存储用户发起的request请求用的
 *              3. 在复写的WebSecurityConfigurerAdapter中，通过如下代码把自定义的SecurityMetadataSource注入到AbstractSecurityInterceptor类中，达到替换DefaultFilterInvocationSecurityMetadataSource的目的
 *                  .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
 *                     @Override
 *                     public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
 *                         fsi.setSecurityMetadataSource(mySecurityMetadataSource());
 *                         return fsi;
 *                     }
 *                 })
 *                  注意：这段代码加入到 .anyRequest().authenticated()//所有请求都要被鉴权 的后面，否者不起作用
 *          方法优缺点：
 *              优点：比起网上的重构FilterSecurityInterceptor，自定义SecurityMetadataSource，重构MyAccessDecisionManager方法后，这种方式对spring鉴权的破坏最小，只是
 *                  把原先DefaultFilterInvocationSecurityMetadataSource类替换成
 *              缺点：废弃了springSecurity原本通过硬编码写死的鉴权。对spring入侵比较大，所有的权限配置/注解全部作废。需要自己在数据库中动态的配置所有权限（包括什么路径，不需要权限验证即permitAll，什么路径需要什么权限。）
 *          故而可以尝试自己重新实现接口FilterInvocationSecurityMetadataSource，达到直接读取数据库，不走配置的目的.
 *      方法二：新增voter投票器
 *          因为真正的鉴权模块是在AccessDecisionManager接口中，由AbstractSecurityInterceptor调用。里面有三个方法：
 *              supports(Class<?> clazz) 表示当前AccessDecisionManager是否支持对应的受保护对象类型
 *              supports(ConfigAttribute attribute) 表示当前AccessDecisionManager是否支持对应的ConfigAttribute
 *              decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) 通过传递的参数来决定用户是否有访问对应受保护对象的权限，里面会调用投票器AccessDecisionVoter接口
 *          AccessDecisionManager接口有三个实现类
 *              AffirmativeBased
 *                  1）只要有AccessDecisionVoter的投票为ACCESS_GRANTED则同意用户进行访问；
 *                  2）如果全部弃权也表示通过；
 *                  3）如果没有一个人投赞成票，但是有人投反对票，则将抛出AccessDeniedException。
 *              ConsensusBased
 *                  1）如果赞成票多于反对票则表示通过。
 *                  2）反过来，如果反对票多于赞成票则将抛出AccessDeniedException。
 *                  3）如果赞成票与反对票相同且不等于0，并且属性allowIfEqualGrantedDeniedDecisions的值为true，则表示通过，否则将抛出异常AccessDeniedException。参数allowIfEqualGrantedDeniedDecisions的值默认为true。
 *                  4）如果所有的AccessDecisionVoter都弃权了，则将视参数allowIfAllAbstainDecisions的值而定，如果该值为true则表示通过，否则将抛出异常AccessDeniedException。参数allowIfAllAbstainDecisions的值默认为false。
 *              UnanimousBased
 *                  UnanimousBased的逻辑与另外两种实现有点不一样，另外两种会一次性把受保护对象的配置属性全部传递给AccessDecisionVoter进行投票，而UnanimousBased会一次只传递一个ConfigAttribute给AccessDecisionVoter进行投票。
 *                  换句话说上面2个是把所有的投票器如：roleVoter，webExpressionVoter等等执行完毕后才会执行AffirmativeBased，而UnanimousBased是执行一个投票器就会执行一遍，具体的逻辑如下：
 *                  1）如果受保护对象配置的某一个ConfigAttribute被任意的AccessDecisionVoter反对了，则将抛出AccessDeniedException。
 *                  2）如果没有反对票，但是有赞成票，则表示通过。
 *                  3）如果全部弃权了，则将视参数allowIfAllAbstainDecisions的值而定，true则通过，false则抛出AccessDeniedException。
 *          AccessDecisionVoter投票器接口，主要实现类如下：
 *              RoleVoter：在投票的时如果拥有该角色即投赞成票。如果ConfigAttribute是以“ROLE_”开头的并且Collection<ConfigAttribute> configAttributes中的ConfigAttribute.getAttribute()不为null会参与投票。
 *              AuthenticatedVoter：其主要用来区分匿名用户、通过Remember-Me认证的用户和完全认证的用户。
 *                    AuthenticatedVoter可以处理的ConfigAttribute有IS_AUTHENTICATED_FULLY、IS_AUTHENTICATED_REMEMBERED和IS_AUTHENTICATED_ANONYMOUSLY。
 *                    如果ConfigAttribute不在这三者范围之内，则AuthenticatedVoter将弃权。否则将视ConfigAttribute而定，如果ConfigAttribute为IS_AUTHENTICATED_ANONYMOUSLY，则不管用户是匿名的还是已经认证的都将投赞成票；
 *                    如果是IS_AUTHENTICATED_REMEMBERED则仅当用户是由Remember-Me自动登录，或者是通过登录入口进行登录认证时才会投赞成票，否则将投反对票；
 *                    而当ConfigAttribute为IS_AUTHENTICATED_FULLY时仅当用户是通过登录入口进行登录的才会投赞成票，否则将投反对票。
 *                    AuthenticatedVoter是通过AuthenticationTrustResolver的isAnonymous()方法和isRememberMe()方法来判断SecurityContextHolder持有的Authentication是否为AnonymousAuthenticationToken或RememberMeAuthenticationToken的，
 *                    即是否为IS_AUTHENTICATED_ANONYMOUSLY和IS_AUTHENTICATED_REMEMBERED
 *          WebExpressionVoter：oter which handles web authorisation decisions.
 *
 *          实现方式：
 *              1.  新增AccessDecisionVoter的实现类，具体实现可以仿照AffirmativeBased或者RoleVoter。
 *              2.  实现vote方法，入参解释下：
 *                      Authentication authentication 为该登录用户拥有的权限
 *                      Object object 在上面有说到 为FilterInvocation，是存储用户发起的request请求用的
 *                      Collection<ConfigAttribute> attributes 为系统中配置的可以访问该路径的角色。具体的路径匹配在DefaultFilterInvocationSecurityMetadataSource类
 *              3. 在congfig中添加自定义的投票器即可,代码如下:
 * 			            http.authorizeRequests().accessDecisionManager(accessDecisionManager());
 *                      @Bean
 *                      public AccessDecisionManager accessDecisionManager() {
 * 			                List<AccessDecisionVoter<? extends Object>> decisionVoters  = Arrays.asList(
 * 				                new RoleVoter(),
 * 				                new WebExpressionVoter(),
 * 				                rolePermissionVoter,
 * 				                new AuthenticatedVoter());
 * 				            return new UnanimousBased(decisionVoters);
 *                      }
 *           方法优缺点：
 *              优点：对springSecurity改写很小，权限既可以用硬编码方式写到代码里面，也可以配置在数据库中。只需要控制好对应对投票器即可
 *              缺点：对springSecurity授权模块代码要比较熟悉。要不然很容易出现漏掉权限判断对情况
 *
 *
 *
 * 但是spring推荐权限这块直接硬编码，写死到代码层面。所以这么设计是否真的合适，未知。
 *
 *
 * 此类暂时废弃，改由新增一个AccessDecisionVoter解决
 *
 * @auther: Jerry
 * @Date: 2019-05-17 19:38
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RolePermissionService rolePermissionService;


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String url = request.getRequestURI();
        Set<String> roleNameSet = rolePermissionService.getRoleByUrl(url);
        if (roleNameSet!=null){
            Set<ConfigAttribute> configAttributes = new HashSet<>();
            for(String roleName:roleNameSet){
                configAttributes.add(new SecurityConfig(roleName));
            }
            return configAttributes;
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {


        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
