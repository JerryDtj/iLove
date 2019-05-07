package com.love.iLove.controller;

import com.love.iLove.domain.User;
import com.love.iLove.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

//    @GetMapping({"/", "/index", "/home"})
//    public String root(){
//        return "index";
//    }




    @PostMapping("/register")
    public String doRegister(User userEntity){
        // 此处省略校验逻辑
        if (userService.insert(userEntity))
            return "redirect:register?success";
        return "redirect:register?error";
    }
}
