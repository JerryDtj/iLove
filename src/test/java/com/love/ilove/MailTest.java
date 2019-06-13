package com.love.ilove;

import com.love.ilove.service.UserInfoService;
import com.love.ilove.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jerry
 * @Date 2019-06-08 18:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MailTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void sendText(){
//        redisTemplate.opsForValue().set("mail-pwd","Ilove1314");
            try {
//                Integer code = userInfoService.emailSend(8,"765747369@qq.com");
//                Assert.assertNotNull(code);
//                Thread.sleep(3000);
                String key = "sendEmail_"+8;
                userInfoService.emailCheck(8,String.valueOf(666944));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
