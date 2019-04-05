package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 */
@Data
@TableName
public class UserDetail {
    @TableId(type = IdType.AUTO)
    int id;
    Integer userId;
    String email;
    Date createTime;
    String nickName;
    Integer gender;
    String province;
    Date year;
    String avatar;
}
