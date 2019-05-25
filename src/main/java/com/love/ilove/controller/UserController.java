package com.love.ilove.controller;

import com.alibaba.fastjson.JSON;
import com.love.ilove.domain.User;
import com.love.ilove.service.*;
import com.love.ilove.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dengtianjiao
 */
@Controller
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRoleService userRoleService;


    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Authentication principal, Model model){
        model.addAttribute("username", principal.getName());
        model.addAttribute("userId",((User) principal.getPrincipal()).getId());
        return "user/user";
    }

    @PutMapping("/updatepwd")
    @ResponseBody
    public ServerResponse updatePwd(User user){
        log.debug("user:{}", JSON.toJSONString(user));
        User u = userService.get(user);
        if (u!=null){
            //用户名密码正确
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (bCryptPasswordEncoder.encode(u.getPassword()).equals(bCryptPasswordEncoder.encode(user.getOldPwd()))){
                int count = userService.update(user);
                ServerResponse.createBySuccess();
            }
        }
        return ServerResponse.createByError();
    }

    @PostMapping("/register")
    public String doRegister(User userEntity){

        Integer result = userRoleService.regist(userEntity.getUsername(),userEntity.getPassword());
        // 此处省略校验逻辑
        if (result!=null){
            return "redirect:register?success";
        }
        return "redirect:register?error";
    }

}
