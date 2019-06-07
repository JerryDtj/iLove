package com.love.ilove.controller;

import com.love.ilove.domain.UserInfo;
import com.love.ilove.service.UserInfoService;
import com.love.ilove.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jerry
 * @Date 2019-06-04 05:42
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping
    public ServerResponse add(@RequestBody @Validated UserInfo userInfo){
        try {
            Integer id= userInfoService.addInfo(userInfo);
            return ServerResponse.createBySuccess(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @GetMapping
    public ServerResponse get(@RequestParam Integer userId){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.valueOf(userId));
         userInfo = userInfoService.get(userInfo);
         if (userInfo!=null){
             return ServerResponse.createBySuccess(userInfo);
         }
         return ServerResponse.createByError();
    }


    public ServerResponse addAvatar(MultipartFile multipartFile){

        return ServerResponse.createByError();
    }
}
