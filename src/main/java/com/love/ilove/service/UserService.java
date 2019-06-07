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

    /**
     * 用户名密码校验接口
     * 用户调用时，会在redis中增加 checkpwd_${username} 的一条记录
     * 格式为{"username":${username},checkTime:${dateTime},count:${count}}，过期时间为1800秒
     * 校验逻辑为：当用户次数大于5次时，那么提示校验失败次数过多，请半小时后在试，
     * @param username
     * @param password
     * @return
     */
    boolean checkpwd(String username, String password);

    /**
     * 更新用户名密码
     * @param username
     * @param password
     * @param oldpwd
     */
    void updatePwd(String username, String password, String oldpwd);

}
