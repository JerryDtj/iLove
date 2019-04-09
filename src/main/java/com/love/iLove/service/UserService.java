package com.love.iLove.service;

import com.love.iLove.domain.User;

public interface UserService {
    User get(User user);

    boolean insert(User userEntity);

    int update(User user);
}
