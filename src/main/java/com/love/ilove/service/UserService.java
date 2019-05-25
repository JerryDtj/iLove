package com.love.ilove.service;

import com.love.ilove.domain.User;

/**
 * @author dengtianjiao
 */
public interface UserService {
    /**
     * 根据User实体类的某个属性查询Use
     * @param user
     * @return
     */
    User get(User user);

    /**
     * 添加
     * @param userEntity
     * @return
     */
    Integer insert(User userEntity);

    /**
     * 更新
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据用户名查询出用户以及对应的权限
     * @param userName
     * @return
     */
    User getUserRoleByUserName(String userName);

}
