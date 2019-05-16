package com.love.iLove.service.impl;

import com.love.iLove.domain.User;
import com.love.iLove.domain.UserInfo;
import com.love.iLove.service.LoginService;
import com.love.iLove.service.UserRoleService;
import com.love.iLove.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @auther: Jerry
 * @Date: 2019-05-15 19:36
 */
@Service("loginService")
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRoleService userRoleService;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean qqLogin(String username, UserInfo userInfo) {

        userRoleService.qqregist(username,"123456",userInfo);

        //自动登录，赋权
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //生成token
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
        User user = new User();
        BeanUtils.copyProperties(userDetails,user);
        String token = jwtTokenUtils.createToken(user);

        redisTemplate.boundValueOps("token_"+user.getUsername()).set(token,10, TimeUnit.SECONDS);

        log.debug("token:{}",token);

        return false;
    }
}
