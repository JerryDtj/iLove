package com.love.ilove.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Jerry
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

    @GetMapping("/photos")
    public String photos(){
        return "user/photos";
    }

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }

    @GetMapping("/errorpage")
    public String authInsuffciently(){
        return "errorpage";
    }
}
