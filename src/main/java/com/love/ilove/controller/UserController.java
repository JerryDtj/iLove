package com.love.ilove.controller;

import com.love.ilove.domain.User;
import com.love.ilove.service.UserRoleService;
import com.love.ilove.service.UserService;
import com.love.ilove.utils.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author dengtianjiao
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRoleService userRoleService;


    @GetMapping
    public String user(@AuthenticationPrincipal Authentication principal, Model model){
        model.addAttribute("username", principal.getName());
        model.addAttribute("userId",((User) principal.getPrincipal()).getId());
        return "user/user";
    }

    @PutMapping("/pwd")
    @ResponseBody
    public ServerResponse updatePwd(@RequestParam String username,@RequestParam String password,@RequestParam String oldpwd){
        try {
            userService.updatePwd(username,password,oldpwd);
        } catch (Exception e) {
            if (e.getMessage().equals("ckeckCountOutOfMax")){
                return ServerResponse.createByErrorMessage("请求次数过多，请稍后在试");
            }
            return ServerResponse.createByErrorMessage("系统异常，请稍后在试");
        }
        return ServerResponse.createBySuccess();
    }

    @PostMapping("/checkpwd")
    @ResponseBody
    public ServerResponse<Boolean> checkPwd(@RequestParam String username,@RequestParam String password){

            try {
                if (userService.checkpwd(username,password)){
                    return ServerResponse.createBySuccess(true);
                }
            } catch (Exception e) {
                if (e.getMessage().equals("ckeckCountOutOfMax")){
                    return ServerResponse.createByErrorMessage("请求次数过多，请稍后在试");
                }
                return ServerResponse.createByErrorMessage("系统异常，请稍后在试");

            }
        return ServerResponse.createBySuccessCodeMessage("密码错误",false);
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
