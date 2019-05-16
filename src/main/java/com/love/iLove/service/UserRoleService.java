package com.love.iLove.service;

import com.love.iLove.domain.UserInfo;

public interface UserRoleService {
    Integer regist(String username, String pwd);

    Integer qqregist(String username, String s, UserInfo userInfo);
}
