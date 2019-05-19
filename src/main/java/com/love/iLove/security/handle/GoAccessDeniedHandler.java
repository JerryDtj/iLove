package com.love.iLove.security.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by macro on 2018/8/6.
 */
@Slf4j
public class GoAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("code:{},message:{}",401,accessDeniedException.getMessage());
        response.sendRedirect("/errorpage");
//        response.setHeader("Content-Type", "application/json;charset=utf-8");
//        response.getWriter().print("{\"code\":401,\"message\":\""+"未认证："+accessDeniedException.getMessage()+"\"}");
//        response.getWriter().flush();
    }
}
