package com.love.iLove.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.love.iLove.domain.User;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @auther: Jerry
 * @Date: 2019-04-11 07:24
 */
public class JwtTokenUtils {

    private String secretString = "secret";
    private String issUser = "jerry";

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
                .withIssuer(issUser)//发布者的url地址
                .withSubject(user.getUsername())
                .withArrayClaim("auth",user.getRoles().toArray(new String[]{}))
                .withExpiresAt(DateUtils.addSeconds(new Date(),2))
                ;
        return builder.sign(algorithmRS);
    }

    /**
     * token 验证
     * @param token
     */
    public Boolean VerifyToken(String token,User user){
        try {
            Algorithm algorithmRS = Algorithm.HMAC256(secretString);
            JWTVerifier verifier = JWT.require(algorithmRS)
                    .withIssuer(issUser)
                    .withSubject(user.getUsername())
                    .withArrayClaim("auth",user.getRoles().toArray(new String[]{}))
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("DecodedJWT:"+ JSON.toJSONString(jwt));
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public void decode(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim("");
            System.out.println(JSON.toJSONString(jwt));
        } catch (JWTDecodeException exception){
            //Invalid token
        }
    }

    public String getUsername(String token){
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public static void main(String[] args) {
        try {
            JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
            String token = jwtTokenUtils.createToken(null);
            jwtTokenUtils.decode(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
