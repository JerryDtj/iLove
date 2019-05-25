package com.love.ilove.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author jerry
 */
@RestController
@RequestMapping("/ip")
public class HelloWord {
    Logger logger = LoggerFactory.getLogger(HelloWord.class);

    @ApiOperation(value = "获取ip",notes = "IP地址获取",tags = "ip地址获取")
    @GetMapping("/address")
    public String address(HttpServletRequest request){
        logger.info("get IP Address start");
        return request.getRemoteAddr();


    }

    @ApiOperation(value = "测试abc",notes = "test1",tags = "ip地址获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "姓名",defaultValue = "Jerry",dataType = "String",required = true),
            @ApiImplicitParam(name = "sex",value = "性别：0男 1女",defaultValue = "0",dataType = "int",paramType = "query",required = true),
            @ApiImplicitParam(name = "type",value = "类型",defaultValue = "true",dataType = "boolean",required = true),
            @ApiImplicitParam(name = "date",value = "时间",defaultValue = "2018-01-03 12:13:23",paramType = "query",dataType = "date",required = false)
    })
    @GetMapping("/abc")
    public String swagger(String name, int sex, boolean type, Date date){
        System.out.println(1111);

        return "visit test";
    }

}
