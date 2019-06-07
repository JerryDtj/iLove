package com.love.ilove.service;

import com.love.ilove.domain.UserInfo;

/**
 * @author dengtianjiao
 */
public interface UserInfoService {
    /**
     * 添加用户详情(不带逻辑校验)
     * @param userInfo
     * @return
     */
//     Integer add(UserInfo userInfo);

    /**
     * 获取用户详情
     * @param userInfo
     * @return
     */
    UserInfo get(UserInfo userInfo);

    /**
     * 添加userInfo时，检测用户是否存在，用户详情是否已经存在
     * @param userInfo
     * @return
     */
    Integer addInfo(UserInfo userInfo);
}
