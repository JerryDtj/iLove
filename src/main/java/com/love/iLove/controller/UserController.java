package com.love.iLove.controller;

import com.alibaba.fastjson.JSON;
import com.love.iLove.domain.User;
import com.love.iLove.service.UserService;
import com.love.iLove.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal, Model model){
        User user = new User();
        user.setUsername(principal.getName());
        user = userService.get(user);
        model.addAttribute("username", principal.getName());
        model.addAttribute("userId",user.getId());
        return "user/user";
    }

    @PutMapping("/updatepwd")
    @ResponseBody
    public ServerResponse updatePwd(User user){
        System.out.println(JSON.toJSONString(user));
        User u = userService.get(user);
        if (u!=null){//用户名密码正确
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.encode(u.getPassword()).equals(bCryptPasswordEncoder.encode(user.getOldPwd()))){
                int count = userService.update(user);
                if (count>0);
                ServerResponse.createBySuccess();
            }
        }
        return ServerResponse.createByError();
    }

}
