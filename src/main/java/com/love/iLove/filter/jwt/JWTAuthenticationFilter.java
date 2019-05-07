package com.love.iLove.filter.jwt;

import com.love.iLove.domain.User;
import com.love.iLove.service.UserService;
import com.love.iLove.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther: Jerry
 * @Date: 2019-04-11 10:43
 */

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 在此方法中检验客户端请求头中的token,
     * 如果存在并合法,就把token中的信息封装到 Authentication 类型的对象中,
     * 最后使用  SecurityContextHolder.getContext().setAuthentication(authentication); 改变或删除当前已经验证的 pricipal
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authInfo = request.getHeader("Authorization");
        String token = StringUtils.removeStart(authInfo, "Bearer ");
//        String token = request.getHeader("token");

        //判断是否有token
        if(StringUtils.isNotBlank(token)) {
            JwtTokenUtils tokenUtils = new JwtTokenUtils();
            String username = tokenUtils.getUsername(token);
            String redisToken = redisTemplate.boundValueOps("token_"+username).get().toString();
            if (redisToken.equals(token)){
                User user = userService.getUserRoleByUserName(username);
                JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
                if (jwtTokenUtils.VerifyToken(token,user)){
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,user.getAuthorities()));
                    //放行
                    chain.doFilter(request, response);
                }
            }
//            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(JWT.decode(token).)
        }else {
            chain.doFilter(request,response);
            return;
        }


//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(token, null,null);
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);




    }

}
