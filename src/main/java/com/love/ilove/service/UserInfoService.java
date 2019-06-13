package com.love.ilove.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.love.ilove.domain.UserInfo;

import javax.mail.MessagingException;

/**
 * @author dengtianjiao
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 获取用户详情
     * @param userInfo
     * @return
     */
    UserInfo get(UserInfo userInfo);

    /**
     * 添加userInfo时，检测用户是否存在，用户详情是否已经存在,存在就更新，不存在就新增
     * @param userInfo
     * @return
     */
//    Integer saveOrUpdate(UserInfo userInfo);

    /**
     * 添加用户详情
     * @param userInfo
     * @return 用户id
     */
    Integer add(UserInfo userInfo);

    /**
     * 更新用户详情
     * @param userInfo
     * @return 更新用户条数
     */
    Integer update(UserInfo userInfo);

    /**
     * 发送email验证码
     * @param userid
     * @param email
     * @return 校验码
     * @throws MessagingException
     */
    int emailSend(int userid, String email) throws MessagingException;

    /**
     * email验证码校验
     * @param userid
     * @param emailCode
     * @throws MessagingException
     */
    void emailCheck(int userid,String emailCode);

    /**
     * 添加或者更新email
     * @param id
     * @param emailCode
     */
    void saveOrUpdateEmail(Integer id, String emailCode);
}
