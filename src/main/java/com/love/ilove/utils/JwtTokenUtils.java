package com.love.ilove.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.love.ilove.domain.User;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: Jerry
 * @Date: 2019-04-11 07:24
 */
@Component
public class JwtTokenUtils {
    @Value("${jwt.secret}")
    private String secretString;
    @Value("${jwt.issUser}")
    private String issUser;
    @Value("${jwt.expiration}")
    private int expiration;


    /**
     * Registered Claims
     *
     * iss: jwt签发者
     *
     * sub: jwt所面向的用户
     *
     * aud: 接收jwt的一方
     *
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     *
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     *
     * iat: jwt的签发时间
     *
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
     * @param user
     * @return
     */
    public String createToken(User user) {
        Algorithm algorithmRS = Algorithm.HMAC256(secretString);
        JWTCreator.Builder builder = JWT.create()
                //发布者的url地址
                .withIssuer(issUser)
                .withSubject(user.getUsername())
                .withArrayClaim("auth",user.getRoles().toArray(new String[]{}))
                .withExpiresAt(DateUtils.addSeconds(new Date(),expiration))
                ;
        return builder.sign(algorithmRS);
    }

    /**
     * token 验证
     * @param token
     */
    public Boolean verifyToken(String token,User user){
        try {
            Algorithm algorithmRS = Algorithm.HMAC256(secretString);
            JWTVerifier verifier = JWT.require(algorithmRS)
                    .withIssuer(issUser)
                    .withSubject(user.getUsername())
                    .withArrayClaim("auth",user.getRoles().toArray(new String[]{}))
                    .build(); //Reusable verifier instance
            verifier.verify(token);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public String getUsername(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

}
