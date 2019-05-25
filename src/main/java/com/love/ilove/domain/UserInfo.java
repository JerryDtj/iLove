package com.love.ilove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.ilove.enums.Gender;
import lombok.Data;

import java.util.Date;

/**
 * 用户详情
 * @author dengtianjiao
 */
@Data
@TableName
public class UserInfo {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer userId;
    String email;
    Date createTime;
    String nickName;
    Gender gender;
    String province;
    Date year;
    String avatar;
}
