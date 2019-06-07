package com.love.ilove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.ilove.domain.User;
import com.love.ilove.domain.UserInfo;
import com.love.ilove.mapper.UserInfoMapper;
import com.love.ilove.service.UserInfoService;
import com.love.ilove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengtianjiao
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    private Integer add(UserInfo userInfo) {
        int count = userInfoMapper.insert(userInfo);
        return count;
    }

    @Override
    public UserInfo get(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(userInfo);
        return userInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer addInfo(UserInfo userInfo) {
        User user = new User();
        user.setId(userInfo.getUserId());
        user = userService.get(user);
        if (user!=null){
            UserInfo info = new UserInfo();
            info.setUserId(userInfo.getUserId());
            info = this.get(info);
            if (info!=null){
                throw new RuntimeException("用户详情已存在");
            }else {
               return this.add(userInfo);
            }
        }else {
            throw new RuntimeException("用户不存在");
        }
    }
}
