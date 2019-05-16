package com.love.iLove.service;

import com.love.iLove.domain.UserInfo;

public interface LoginService {
    /**
     * qq登录
     * 1。更具username和默认密码（123456）自动创建用户
     * 2。根据userInfo 自动保存获取到的用户详情信息
     * @param username
     * @param userInfo 没有userId
     * @return
     */
    boolean qqLogin(String username, UserInfo userInfo);
}
