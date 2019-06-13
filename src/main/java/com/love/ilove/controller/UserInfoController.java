package com.love.ilove.controller;

import com.love.ilove.domain.UserInfo;
import com.love.ilove.service.UserInfoService;
import com.love.ilove.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jerry
 * @Date 2019-06-04 05:42
 */
@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;



    @PostMapping
    public ServerResponse add(@RequestBody @Validated UserInfo userInfo){
        try {
            userInfo.setEmail(null);
            Integer id= userInfoService.add(userInfo);
            return ServerResponse.createBySuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @PutMapping
    public ServerResponse update(@RequestBody @Validated UserInfo userInfo){
        try {
            userInfo.setEmail(null);
            Integer id= userInfoService.update(userInfo);
            return ServerResponse.createBySuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @PostMapping("/saveorupdate")
    public ServerResponse saveorupdate(@RequestBody @Validated UserInfo userInfo){
        try {
            userInfo.setEmail(null);
            Assert.isTrue(userInfoService.saveOrUpdate(userInfo),"保存或者更新失败");
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @GetMapping
    public ServerResponse get(@RequestParam Integer userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Integer.valueOf(userId));
         userInfo = userInfoService.get(userInfo);
         if (userInfo!=null){
             return ServerResponse.createBySuccess(userInfo);
         }
         return ServerResponse.createByError();
    }

    @GetMapping("/emailSend")
    public ServerResponse emailSend(@Validated UserInfo userInfo){
        try {
            Assert.notNull(userInfo.getEmail(),"邮箱地址不能为空");
            userInfoService.emailSend(userInfo.getId(),userInfo.getEmail());
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error("",e);
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @GetMapping("/emailCheck")
    public ServerResponse emailCheck(@RequestParam int userId,@RequestParam String emailCode){
        try {
            userInfoService.emailCheck(userId,emailCode);
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error("",e);
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @PostMapping("/saveOrUpdateEmail")
    public ServerResponse emailSaveOrUpdte(@Validated UserInfo userInfo){
        try {
             userInfoService.saveOrUpdateEmail(userInfo.getId(),userInfo.getEmail());
            return ServerResponse.createBySuccess();
        } catch (Exception e) {
            log.error("",e);
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }
}
