package com.love.ilove.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.ilove.domain.User;
import com.love.ilove.mapper.UserMapper;
import com.love.ilove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author dengtianjiao
 */
@SuppressWarnings("unchecked")
@Service("/userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User get(User user) {
        QueryWrapper<User> wrapper  = new QueryWrapper();
        wrapper.setEntity(user);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public Integer insert(User userEntity) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",userEntity.getUsername());
        User u = userMapper.selectOne(wrapper);
        if (u==null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
            userMapper.insert(userEntity);
            return userEntity.getId();
        }else {
            return u.getId();
        }
    }

    @Override
    public int update(User user) {
        int count = userMapper.update(user,null);
        return count;
    }

    @Override
    public User getUserRoleByUserName(String userName) {
        return userMapper.getUserRoleByUserName(userName);
    }

    @Override
    public boolean checkpwd(String username, String password) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String checkKey = "checkpwd_"+username;
        Object o = valueOperations.get(checkKey);
        JSONObject jsonObject;
        if (o==null){
            jsonObject = new JSONObject();
            jsonObject.put("username",username);
            jsonObject.put("startTime",new Date());
            jsonObject.put("count",1);

        }else {
            String val = o.toString();
            jsonObject = JSON.parseObject(val);
            if (jsonObject.getInteger("count")>5){
                throw new RuntimeException("ckeckCountOutOfMax");
            }else {
                jsonObject.put("count",jsonObject.getIntValue("count")+1);
            }
        }
        valueOperations.set(checkKey,jsonObject,30, TimeUnit.MINUTES);
        User user = new User();
        user.setUsername(username);
        user = this.get(user);
        if (user!=null){
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            return bCryptPasswordEncoder.matches(password,user.getPassword());

        }
        return false;
    }

    @Override
    public void updatePwd(String username, String password, String oldpwd) {
        if (this.checkpwd(username,oldpwd)){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            this.update(user);
        }
    }
}
