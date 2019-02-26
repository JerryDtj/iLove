package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.User;
import com.love.iLove.mapper.UserMapper;
import com.love.iLove.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("/userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User get(String userName) {
        QueryWrapper<User> wrapper  = new QueryWrapper();
        wrapper.eq("username",userName);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public boolean insert(User userEntity) {
        User u = new User();
        BeanUtils.copyProperties(userEntity,u);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        userMapper.insert(u);
        return true;
    }
}
