package com.love.ilove.controller;

import com.love.ilove.domain.User;
import com.love.ilove.utils.QiNiuUtils;
import com.love.ilove.utils.ServerResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jerry
 * @Date 2019-06-06 09:22
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuController {
    private String BucketName = "lovephoto";
    private String BucketUrl = "http://psk85spi5.bkt.clouddn.com/";

    @GetMapping("/token")
    public ServerResponse getToken(@AuthenticationPrincipal Authentication principal){
        String userId = ((User) principal.getPrincipal()).getId().toString();
        String token = QiNiuUtils.getTokenImg(BucketName,userId);
        if (!BucketUrl.endsWith("/")){
            BucketUrl +="/";
        }
        String url = BucketUrl+userId;
        Map result = new HashMap();
        result.put("token",token);
        result.put("url",url);
        return ServerResponse.createBySuccess(result);
    }

    @RequestMapping("/getName")
    public String getName(){
        return "测试";
    }
}
