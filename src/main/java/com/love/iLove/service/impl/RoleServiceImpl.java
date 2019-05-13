package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.Role;
import com.love.iLove.mapper.RoleMapper;
import com.love.iLove.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: Jerry
 * @Date: 2019-05-10 05:48
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role get(Role r) {
        QueryWrapper<Role> wrapper  = new QueryWrapper();
        wrapper.setEntity(r);
        return roleMapper.selectOne(wrapper);
    }
}
