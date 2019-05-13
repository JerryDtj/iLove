package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.Role;
import com.love.iLove.domain.User;
import com.love.iLove.domain.UserRole;
import com.love.iLove.enums.RoleStatus;
import com.love.iLove.enums.UserRoleStatus;
import com.love.iLove.mapper.UserRoleMapper;
import com.love.iLove.service.UserRoleService;
import com.love.iLove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Integer regist(String username, String password){
        User user = new User();
        user.setUsername(username);
        User u = userService.get(user);
        if (u==null){
            user.setPassword(password);
            Integer userId = userService.insert(user);
            if (userId!=null){
                Role role = new Role();
                role.setName("REPORTADMIN");
                role.setRoleStatus(RoleStatus.NORMALITY);
                role = roleService.get(role);
                if (role.getId()!=null){
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(role.getId());
                    userRole.setUserRoleStatus(UserRoleStatus.NORMALITY);

                    QueryWrapper wrapper = new QueryWrapper<UserRole>();
                    wrapper.setEntity(userRole);
                    UserRole result = userRoleMapper.selectOne(wrapper);
                    if (result==null){
                        int count = userRoleMapper.insert(userRole);
                        if (count>0){
                            return userRole.getId();
                        }else {
                            throw new RuntimeException("mybatis error");
                        }
                    }else {
                        return result.getId();
                    }
                }
            }
        }//用户已经注册过了
        return null;
    }
}
