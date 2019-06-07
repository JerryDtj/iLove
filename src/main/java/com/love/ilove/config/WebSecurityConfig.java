package com.love.ilove.config;

import com.love.ilove.filter.jwt.JwtAuthenticationFilter;
import com.love.ilove.handler.LoginSuccessHandler;
import com.love.ilove.security.handle.GoAccessDeniedHandler;
import com.love.ilove.security.handle.GoAuthenticationEntryPoint;
import com.love.ilove.security.service.UserDetailsServiceImpl;
import com.love.ilove.security.voter.RolePermissionVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author dengtianjiao
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private RolePermissionVoter rolePermissionVoter;

    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/register","/AboutMe.html","/login/**","/errorpage").permitAll()
                //过滤swagger的请求
                .antMatchers("/swagger-ui.html","/webjars/**","/v2/**","/swagger-resources/**").permitAll()
                //测试环境，不过滤任何请求
//                .antMatchers("/user/**").hasRole("USERINFO")
                //所有请求都要被鉴权
//                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new GoAccessDeniedHandler())
                .authenticationEntryPoint(new GoAuthenticationEntryPoint())
                .and()
                .formLogin().loginPage("/login")
                //添加自定义登录成功处理页面
                .successHandler(loginSuccessHandler)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .csrf().disable()
//                 自定义accessDecisionManager
//                .authorizeRequests().accessDecisionManager(accessDecisionManager())


        ;


        // 在 UsernamePasswordAuthenticationFilter 前添加 JwtAuthenticationFilter
            http.addFilterAt(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
        .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        List<AccessDecisionVoter<? extends Object>> decisionVoters
//                = Arrays.asList(
//                        new RoleVoter(),
//                new WebExpressionVoter(),
//                rolePermissionVoter,
//                new AuthenticatedVoter());
//        return new UnanimousBased(decisionVoters);
//    }


}
