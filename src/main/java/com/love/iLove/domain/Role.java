package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.iLove.enums.RoleStatus;
import lombok.Data;

/**
 * @auther: Jerry
 * @Date: 2019-04-18 17:45
 */
@Data
@TableName
public class Role {
    @TableId(type = IdType.AUTO)
    int id;
    String name;
    RoleStatus roleStatus;
}
