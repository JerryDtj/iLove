package com.love.iLove.service;

import com.love.iLove.domain.User;

public interface UserService {
    User get(String userName);

    boolean insert(User userEntity);
}
