package com.love.ilove.controller;

import com.love.ilove.domain.User;
import com.love.ilove.utils.QiNiuUtils;
import com.love.ilove.utils.ServerResponse;
import com.qiniu.common.QiniuException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jerry
 * @Date 2019-06-06 09:22
 */
@Slf4j
@RestController
@RequestMapping("/qiniu")
public class QiniuController {
    private String publicBucketName = "lovephoto";
    private String secretBucketName = "ilove";
    private String BucketUrl = "http://psk85spi5.bkt.clouddn.com/";

    @GetMapping("/avatar")
    public ServerResponse getAvatar(@AuthenticationPrincipal Authentication principal){
        try {
            String userId;
            Object p = principal.getPrincipal();
            if (p instanceof User){
                userId = ((User)p).getId().toString();
            }else if (p instanceof String){
                userId = principal.getPrincipal().toString();
            }else {
                throw new RuntimeException("获取userId出错");
            }

            String token = QiNiuUtils.getImgToken(publicBucketName,userId);
            String bucketUrl = String.format("http://%s/",QiNiuUtils.getDomain(publicBucketName));
            String url = bucketUrl+userId;
            Map result = new HashMap();
            result.put("token",token);
            result.put("url",url);
            return ServerResponse.createBySuccess(result);
        } catch (QiniuException e) {
            log.error("",e);
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
    }

    @GetMapping("/token")
    public ServerResponse getToken(@RequestParam String bucketName){
        switch (bucketName){
            case "pub":
                bucketName = publicBucketName;
                break;
            case "sec":
                bucketName = secretBucketName;
                break;
            default:
                return ServerResponse.createByErrorMessage("存储名称有误");
        }
        return ServerResponse.createBySuccess(QiNiuUtils.getImgToken(bucketName,null));
    }

    @PutMapping("/getPicUrl")
    public ServerResponse getPicUrl(String id){
        String userId = "8";
        try {
            List<String> bigPic = QiNiuUtils.getSecretDolandLoadUrl(secretBucketName,userId,null);
            List<String> smallPic = QiNiuUtils.getSecretDolandLoadUrl(secretBucketName,userId,"@photo200");
            List<Map> result = new ArrayList<>();
            for (int i =0;i<bigPic.size();i++){
                Map m = new HashMap();
                m.put("bigPic",bigPic.get(i));
                m.put("smallPic",smallPic.get(i));
                result.add(m);
            }
            return ServerResponse.createBySuccess(result);
        } catch (QiniuException e) {
            e.printStackTrace();
            log.error("",e);
        }
        return ServerResponse.createByError();
    }

    @RequestMapping("/getName")
    public String getName(){
        return "测试";
    }
}
