package com.love.ilove.service;

import com.love.ilove.domain.UserInfo;

/**
 * @author dengtianjiao
 */
public interface UserRoleService {
    /**
     * 根据用户名密码，创建用户并赋予'USER'权限
     * @param username
     * @param pwd
     * @return
     */
    Integer regist(String username, String pwd);

    /**
     * 更具用户名，密码，用户详情，自动创建用户，用户详情，并赋予'USERINFO'权限
     * @param username
     * @param s
     * @param userInfo
     * @return
     */
    Integer qqregist(String username, String s, UserInfo userInfo);
}
