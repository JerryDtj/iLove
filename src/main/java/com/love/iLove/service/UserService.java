package com.love.iLove.service;

import com.love.iLove.domain.User;

public interface UserService {
    User get(User user);

    Integer insert(User userEntity);

    int update(User user);

    User getUserRoleByUserName(String userName);
}
