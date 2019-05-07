package com.love.iLove.handler;

import com.alibaba.fastjson.JSON;
import com.love.iLove.domain.User;
import com.love.iLove.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @auther: Jerry
 * @Date: 2019-04-03 08:11
 */
@Component("loginSuccessHandler")
@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录成功处理器
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.debug("auth:{}",JSON.toJSONString(authentication));
        User user = (User) authentication.getPrincipal();
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();

        String token = jwtTokenUtils.createToken(user);

        redisTemplate.boundValueOps("token_"+user.getUsername()).set(token,10, TimeUnit.SECONDS);
        response.setHeader("Authorization", token);

        log.debug("token:{}",token);
        super.getRedirectStrategy().sendRedirect(request, response, "/user");//登录成功跳转页面
//            super.onAuthenticationSuccess(request, response, authentication);//登录失败原地址跳转
    }

}
