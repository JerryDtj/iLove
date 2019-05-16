package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.Role;
import com.love.iLove.domain.User;
import com.love.iLove.domain.UserInfo;
import com.love.iLove.domain.UserRole;
import com.love.iLove.enums.RoleStatus;
import com.love.iLove.enums.UserRoleStatus;
import com.love.iLove.mapper.UserInfoMapper;
import com.love.iLove.mapper.UserRoleMapper;
import com.love.iLove.service.UserRoleService;
import com.love.iLove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * @auther: Jerry
 * @Date: 2019-05-10 05:49
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional
    public Integer regist(String username, String password){
       return this.registByRoleName(username,password,"USER");
    }

    private Integer registByRoleName(String username, String password,String roleName){
        User user = new User();
        user.setUsername(username);
        User u = userService.get(user);
        if (u==null){
            user.setPassword(password);
            Integer userId = userService.insert(user);
            if (userId!=null){
                Role role = new Role();
                role.setName(roleName);
                role.setRoleStatus(RoleStatus.NORMALITY);
                role = roleService.get(role);
                if (role.getId()!=null){
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(role.getId());
                    userRole.setUserRoleStatus(UserRoleStatus.NORMALITY);
                    this.insert(userRole);
                    return userId;
                }
            }
        }//用户已经注册过了
        return null;
    }

    @Transient
    public Integer qqregist(String username, String password, UserInfo userInfo){
        Integer userId = this.registByRoleName(username,password,"USERINFO");

        if (userId!=null){
            //用户没有注册过
            userInfo.setUserId(userId);
            userInfoMapper.insert(userInfo);
        }
        return userId;
    }



    public Integer insert(UserRole userRole){
        QueryWrapper wrapper = new QueryWrapper<UserRole>();
        wrapper.setEntity(userRole);
        UserRole result = userRoleMapper.selectOne(wrapper);
        if (result==null){
            userRoleMapper.insert(userRole);
            result = userRole;
        }
        return result.getId();

    }
}
