# iLove

## 项目地址

  https://tianzijiaozi.top
  
  **服务器介绍**
  
   阿里云服务器 1核 2g

## 项目介绍

一个基于gradle的婚恋网站. 
  - 下载运行需要先集成gradle
  - gradle下载安装请参考官网 https://gradle.org/install/
  - 所有的新功能/问题我都会先发布一个isuess出来,然后在master分支上建立出对应isuess的功能.完成后会把对应的isuess合并到master
  - 所有的部署全部依靠jekins自动部署,di工具暂时还没有.
  
## 技术栈
  - Spring Security
  - Spring Security OAuth2
  - myBatis
  - myBatis plus
  - JWT
  - Redis
  - Thymeleaf
  - Bootstrap
  
## 项目搭建

  **运行环境：**

    jdk1.8+gradle。
  
  **数据库配置：**
 
  数据库mysql
  
## 项目计划

  **已实现功能**

    1. 第三方登录 qq,登录时自动注册账户，添加角色
    2. 注册时自动添加用户角色
    3. 权限系统:角色 对应不同的权限（访问路径）,现在权限可以在数据库中或代码中配置
    4. qq登录时，自动填充用户详细信息

  **未实现功能**

    1. 前后端分离,前段vue不是很会写,欢迎会vue的同学参与
    2. 用户列表页
    3. 站内信模块 --后期考虑用rabbitmq实现
    4. 用户详情模块
    5. 用户名模块
    6. 权限模块,动态权限配置.修改数据库中的权限角色表
