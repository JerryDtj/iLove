package com.love.iLove.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ip")
public class HelloWord {
    Logger logger = LoggerFactory.getLogger(HelloWord.class);

    @GetMapping("/address")
    public String address(HttpServletRequest request){
        logger.info("get IP Address start");
        return request.getRemoteAddr();
    }

}
