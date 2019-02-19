package com.love.iLove.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 是否锁定
     */
    private boolean accountNonLocked;

    /**
     * 是否过期
     */
    private boolean accountNonExpired;

    /**
     * 权限
     */
    private String roles;
}
