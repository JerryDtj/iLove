package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.mapper.UserMapper;
import com.love.iLove.pojo.User;
import com.love.iLove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("/userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public User get(String userName) {
        QueryWrapper<User> wrapper  = new QueryWrapper();
        wrapper.eq("username",userName);
        User user = mapper.selectOne(wrapper);
        return user;
    }
}
