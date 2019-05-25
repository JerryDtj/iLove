package com.love.ilove.filter.jwt;

import com.love.ilove.domain.User;
import com.love.ilove.service.UserService;
import com.love.ilove.utils.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: Jerry
 * @Date: 2019-04-11 10:43
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.jwtHead}")
    private String jwtHead;



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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authInfo = request.getHeader(tokenHeader);
        String token = StringUtils.removeStart(authInfo, jwtHead);

        //判断是否有token
        if(StringUtils.isNotBlank(token)) {
            String username = jwtTokenUtils.getUsername(token);
            Object o = redisTemplate.opsForValue().get("token_"+username);
            if (o!=null){
                String redisToken = o.toString();
                if (redisToken.equals(token)){

                    User user = userService.getUserRoleByUserName(username);
                    if (jwtTokenUtils.verifyToken(token,user)){
                        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username,null,user.getAuthorities()));

                    }
                }
            }
        }

        //不管成功与否，都放行，没成功的没有办法通过权限验证
        chain.doFilter(request, response);
    }

}
