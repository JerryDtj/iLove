package com.love.ilove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author: Jerry
 * @Date: 2019-05-24 07:55
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //设置api信息
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                //any() 对所有api进行监控,basePackage单路径扫描
                .apis(RequestHandlerSelectors.basePackage("com.love.ilove"))
                //可以根据url路径设置哪些请求加入文档，忽略哪些请求 any 对所有api进行监控
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                ;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档的标题
                .title("ILove后端 接口文档示例")
                //设置文档的描述->1.Overview
                .description("更多内容请关注：https://github.com/JerryDtj")
                //设置文档的版本信息->
                .version("1.0")
                //设置文档的联系方式->1.2 Contact information
                .contact(new Contact("Jerry", "https://tianzijiaozi.top", "jiao_snow@126.com"))
                //设置文档的License信息->1.3 License information
                .termsOfServiceUrl("tianzijiaozi.top")
                .build();
    }

    private ApiKey apiKey(){
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                //更具正则判断那些路径需要授权
                .forPaths(PathSelectors.regex("/ip/.*"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}
