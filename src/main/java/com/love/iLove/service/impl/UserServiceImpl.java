package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.User;
import com.love.iLove.mapper.UserMapper;
import com.love.iLove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@SuppressWarnings("unchecked")
@Service("/userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

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
}
