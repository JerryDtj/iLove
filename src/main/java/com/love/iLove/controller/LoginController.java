package com.love.iLove.controller;

import com.alibaba.fastjson.JSONObject;
import com.love.iLove.domain.UserInfo;
import com.love.iLove.enums.Gender;
import com.love.iLove.service.LoginService;
import com.love.iLove.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @auther: Jerry
 * @Date: 2019-05-12 10:11
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    private String APP_ID = "101555884";
    private String APP_Key = "f971d329363d602c0b24e08230c7756d";
    private String redirect_uri = "https://tianzijiaozi.top/login/qqLogin";



    @Autowired
    private LoginService loginService;


    @GetMapping("/qqLogin")
    public String qqLogin(@RequestParam String code){
        String qqtoken = this.getQQToken(code);
        if (StringUtils.isNotBlank(qqtoken)){
            String openId = this.getUserOpenId(qqtoken);//获取用户私人id
            if (StringUtils.isNotBlank(openId)){
                UserInfo userInfo = this.getUserInfo(qqtoken,openId);
                loginService.qqLogin(openId,userInfo);
            }
        }
        return "redirect:/user";
    }

    /**
     * 获取qq登录token
     * @param code
     * @return
     */
    public String getQQToken(String code){
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";
        String result = this.sendGet(String.format(url,APP_ID,APP_Key,code,redirect_uri));
        //返回结果类似于access_token=9E25C5025CA320C9C2EFEEDB8B2221BC&expires_in=7776000&refresh_token=77DCA193CB84F2A210C6749C8991F117
        if (result.contains("access_token")){
            String token = result.substring(result.indexOf("access_token="),result.indexOf("&expires_in")).replace("access_token=","");
            return token;
        }
        return null;
    }

    /**
     * 根据qq登录token 获取用户id
     * @param qqtoken
     * @return
     */
    public String getUserOpenId(String qqtoken){
        String url = "https://graph.qq.com/oauth2.0/me?access_token=%s";
        String result = this.sendGet(String.format(url,qqtoken));
        //返回结果类似于callback( {"client_id":"101555884","openid":"D70116D14219DB5260B461CA7AE6BE02"} );
        JSONObject jsonObject = JSONObject.parseObject(result.substring(result.indexOf("callback( "),result.indexOf(" );")).replace("callback( ",""));
        return jsonObject.getString("openid");
    }

    /**
     * 更具qq登录token和用户id获取用户信息
     * @param qqToken
     * @param openid
     * @return
     */
    public UserInfo getUserInfo(String qqToken, String openid){
        String url = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
        String result = this.sendGet(String.format(url,qqToken,APP_ID,openid));
        //返回结果为一个json串
        UserInfo userInfo = null;
        JSONObject jsonObject = JSONObject.parseObject(result);
        System.out.println("json:"+jsonObject.toJSONString());
        if (jsonObject.getInteger("ret")==0){
            //成功登录
            userInfo = jsonObject.toJavaObject(UserInfo.class);
            userInfo.setGenderEnum(EnumUtil.getEnumByValue(Gender.class,jsonObject.getString("gender")));
            userInfo.setProvince(jsonObject.getString("province")+jsonObject.getString("city"));
        }

        return userInfo;
    }

    private String sendGet(String url){
        String result = "";
        BufferedReader in = null;
        try {
            URL sendUrl = new URL(url);
            URLConnection connection = sendUrl.openConnection();
            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
}
