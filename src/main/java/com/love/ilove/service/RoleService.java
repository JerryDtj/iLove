package com.love.ilove.service;

import com.love.ilove.domain.Role;

/**
 * @author dengtianjiao
 */
public interface RoleService {
    /**
     * 根据权限的属性值查找出具体的权限
     * @param r
     * @return
     */
    Role getRole(Role r);
}
