package com.love.ilove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.ilove.domain.Role;
import com.love.ilove.mapper.RoleMapper;
import com.love.ilove.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author  Jerry
 * @Date: 2019-05-10 05:48
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRole(Role r) {
        QueryWrapper<Role> wrapper  = new QueryWrapper();
        wrapper.setEntity(r);
        return roleMapper.selectOne(wrapper);
    }

}
