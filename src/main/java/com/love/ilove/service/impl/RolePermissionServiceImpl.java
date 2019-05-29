package com.love.ilove.service.impl;

import com.love.ilove.mapper.RoleMapper;
import com.love.ilove.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: Jerry
 * @Date: 2019-05-17 20:21
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Set<String> getRoleByUrl(String url) {
        List<String> list = roleMapper.getNameByUrl(url);
        Set<String> set = new HashSet(list);
//        Set<String> roleSet = new HashSet<>();
//        roleSet.add("USERDO");
//        roleSet.add("USER");
        return set;
    }
}
