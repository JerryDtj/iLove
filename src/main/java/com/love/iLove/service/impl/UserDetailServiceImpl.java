package com.love.iLove.service.impl;

import com.love.iLove.domain.UserDetail;
import com.love.iLove.mapper.UserDetailsMapper;
import com.love.iLove.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserDetailsMapper userDetailsMapper;


    @Override
    public UserDetail getDetilById(int id) {
        return userDetailsMapper.selectById(id);
    }
}
