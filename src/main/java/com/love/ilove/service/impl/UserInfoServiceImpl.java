package com.love.ilove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.ilove.domain.User;
import com.love.ilove.domain.UserInfo;
import com.love.ilove.mapper.UserInfoMapper;
import com.love.ilove.service.UserInfoService;
import com.love.ilove.service.UserService;
import com.love.ilove.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author dengtianjiao
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper,UserInfo> implements UserInfoService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MailUtil mailUtil;
    @Value("${emai.maxSendNum}")
    private int maxSendNum;
    @Value("${emai.maxCheckNum}")
    private int maxCheckNum;
    @Value("${emai.keepMins}")
    private Long keepMins;

    @Override
    public Integer add(UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getId());
        Assert.isNull(userService.get(user),"用户不存在");
        UserInfo info = new UserInfo();
        info.setId(userInfo.getId());
        Assert.notNull(this.get(userInfo),"用户详情已存在");
        userInfoMapper.insert(userInfo);
        return userInfo.getId();
    }

    @Override
    public Integer update(UserInfo userInfo){
        return userInfoMapper.updateById(userInfo);
    }

    /**
     * 邮箱验证码校验，在有效期内只能校验5次
     * @param userid
     * @param emailCode
     */
    @Override
    public void emailCheck(int userid, String emailCode) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "sendEmail_"+userid;
        Assert.isTrue(redisTemplate.hasKey(key),"校验码已经过期。");
        Assert.isTrue(Integer.valueOf(hashOperations.get(key,"checkNum").toString())<=maxCheckNum,"重试次数过多，请稍后在试");
        int radom = Integer.parseInt(hashOperations.get(key,"radom").toString());
        if (radom!=Integer.valueOf(emailCode)){
            hashOperations.increment(key,"checkNum",1);
            throw new RuntimeException("邮箱验证码不正确");
        }else {
            hashOperations.put(key,"checkStatus","true");
        }

    }

    @Override
    public void saveOrUpdateEmail(Integer userId, String email) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "sendEmail_"+userId;
        Assert.isTrue("true".equals(hashOperations.get(key,"checkStatus")),"邮箱验证码不正确");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setEmail(email);
        this.saveOrUpdate(userInfo);
    }

    @Override
    public int emailSend(int userid, String email) throws MessagingException {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "sendEmail_"+userid;
        int radom;
        if (redisTemplate.hasKey(key)){
            int sendNum = Integer.parseInt(hashOperations.get(key,"sendNum").toString());
            Assert.isTrue(sendNum<maxSendNum,"邮件发送次数过多，请稍后在试");
            radom = Integer.parseInt(hashOperations.get(key,"radom").toString());
            hashOperations.increment(key,"sendNum",1);
        }else {
            radom = new Random().nextInt(999999);
            Map map = new HashMap(3);
            map.put("sendNum",0);
            map.put("checkNum",0);
            map.put("radom",radom);
            redisTemplate.opsForHash().putAll(key,map);
            redisTemplate.expire(key,keepMins,TimeUnit.MINUTES);
        }

        String content = radom+" 为您的验证码，"+keepMins+"钟内有效 【iLove 官网】";
        mailUtil.sendTextMail(new String[]{email},content,"ILove验证码");
        return radom;
    }



    @Override
    public UserInfo get(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(userInfo);
        return userInfoMapper.selectOne(queryWrapper);
    }

}
