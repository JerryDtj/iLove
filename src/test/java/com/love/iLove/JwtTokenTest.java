package com.love.iLove;

import com.love.iLove.domain.User;
import com.love.iLove.utils.JwtTokenUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @auther: Jerry
 * @Date: 2019-04-14 10:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenTest {

    private User user;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;



    @Before
    public void getUser(){
        user = new User();
        user.setPassword("3");
        user.setUsername("3");
        List<String> roles = new ArrayList<>();
        roles.add("USERDO");
        user.setRoles(roles);
    }

    @Test
    public void creatToken(){
        String token = jwtTokenUtils.createToken(user);
        Assert.assertNotNull(token);
        redisTemplate.opsForValue().set("token_3",token,expiration, TimeUnit.SECONDS);
    }

    @Test
    public void VerifyToken(){
        JwtTokenUtils jwtTokenUtils = new JwtTokenUtils();
        String token = jwtTokenUtils.createToken(user);
        Boolean result = jwtTokenUtils.VerifyToken(token,user);
        Assert.assertTrue(result);
    }
}
