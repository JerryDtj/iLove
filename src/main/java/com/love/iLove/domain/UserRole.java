package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.iLove.enums.UserRoleStatus;
import lombok.Data;

/**
 * @auther: Jerry
 * @Date: 2019-05-10 06:14
 */
@TableName
@Data
public class UserRole {
    @TableId(value = "user_role_id",type = IdType.AUTO)
    public Integer id;
    private Integer userId;
    private Integer roleId;
    private UserRoleStatus userRoleStatus;
}
