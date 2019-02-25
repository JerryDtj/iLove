package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Data
@EntityScan
public class QQUser {

    @TableId(type = IdType.AUTO)
    private String id;

    private String username;

    private String password;

    private boolean enable=true;

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

    private String roles;
}
