package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName
public class User {

    @TableId(type = IdType.AUTO)
    private String id;

    private String username;

    private String password;

    private boolean enabled=true;

    private boolean accountNonLocked = false;

    private boolean accountNonExpired = false;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 省份
     */
    private String province;

    /**
     * 出生年
     */
    private String year;

    /**
     * 头像
     */
    private String avatar;

    private String roles = "ROLE_USER";
}
