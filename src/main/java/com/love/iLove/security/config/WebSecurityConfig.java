package com.love.iLove.security.config;

import com.love.iLove.filter.jwt.JWTAuthenticationFilter;
import com.love.iLove.handler.LoginSuccessHandler;
import com.love.iLove.security.handle.GoAccessDeniedHandler;
import com.love.iLove.security.handle.GoAuthenticationEntryPoint;
import com.love.iLove.security.service.AnyUserDetailsService;
import com.love.iLove.security.voter.RolePermissionVoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AnyUserDetailsService anyUserDetailsService;

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
                .antMatchers("/","/register","/login/**","/errorpage").permitAll()
                .antMatchers("/user/**").hasAnyRole("USERDO","USER")
                .anyRequest().authenticated()//所有请求都要被鉴权
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new GoAccessDeniedHandler())
                .authenticationEntryPoint(new GoAuthenticationEntryPoint())
                .and()
                .formLogin().loginPage("/login")
                .successHandler(loginSuccessHandler)//添加自定义登录成功处理页面
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .csrf().disable()
//                 自定义accessDecisionManager
                .authorizeRequests().accessDecisionManager(accessDecisionManager())


        ;


        // 在 UsernamePasswordAuthenticationFilter 前添加 JWTAuthenticationFilter
            http.addFilterAt(jwtAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(anyUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder())
        ;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                        new RoleVoter(),
                new WebExpressionVoter(),
                rolePermissionVoter,
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }


}
