package com.love.iLove.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther: Jerry
 * @Date: 2019-05-08 07:09
 */
@Controller
public class Jump {
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }
}
