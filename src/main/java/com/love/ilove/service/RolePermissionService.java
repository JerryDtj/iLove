package com.love.ilove.service;

import java.util.Set;

/**
 * @author dengtianjiao
 */
public interface RolePermissionService {
    /**
     * 更具url地址查询这个地址的所有权限
     * @param url
     * @return
     */
    Set<String> getRoleByUrl(String url);
}
