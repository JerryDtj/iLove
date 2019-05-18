package com.love.iLove.security.voter;

import com.love.iLove.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

/**
 * @auther: Jerry
 * @Date: 2019-05-18 18:58
 */
@Component
public class RolePermissionVoter implements AccessDecisionVoter<Object> {

    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 对于用户权限的判断。
     * RoleVoter中默认实现方式为判断权限是否以"ROLE_"开头
     * 这里就不做任何判断了，后面如果有什么判断可以修改这里的代码
     * @param attribute
     * @return true
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /**
     * 指示AccessDecisionVoter实现是否能够为指示的安全对象类型提供访问控制投票
     *
     * @param clazz the secure object
     *
     * @return always <code>true</code>
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * 真正的鉴权处理。
     *      返回值有3种：ACCESS_GRANTED 同意，ACCESS_ABSTAIN 弃权，ACCESS_DENIED 拒绝
     * 此处处理逻辑如下：
     *      1. 权限和请求路径都不能为空，如果为空，直接拒绝
     *      2. 如果attributes不为空，也就是说系统中已经配置了对应的权限，那么直接弃权，交由roleVoter处理
     *      3. 如果attributes为空，那么去数据库中查询对应的权限。有就通过，没有就拒绝
     * @param authentication 登录用户拥有的权限
     * @param object 访问请求 HttpServletRequest
     * @param attributes 需要的权限，系统或者数据中配置
     * @return
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        //如果没有获取到权限，或者请求路径为空 直接拒绝
        //及时不访问任何路径，直接访问localhost:8080，object的值也是"/"
        if (authentication == null||object == null||attributes==null) {
            return ACCESS_DENIED;//-1： 拒绝 0：弃权 1：同意
        }

        HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
        String url = request.getRequestURI();
        Set<String> roleNameSet = rolePermissionService.getRoleByUrl(url);

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority:grantedAuthorities){
            if (roleNameSet.contains(grantedAuthority.getAuthority())){
                return ACCESS_GRANTED;
            }
        }
        return ACCESS_ABSTAIN;
    }
}
