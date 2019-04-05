package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class User {

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private String password;

    private boolean enabled=true;

    private boolean accountNonLocked = false;

    private boolean accountNonExpired = false;

    private String roles = "ROLE_USER";
}
