package com.love.ilove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.ilove.enums.Gender;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "userId不能为空")
    Integer userId;
    @Email(message = "邮箱格式不正确")
    String email;
    Date createTime;
    @NotBlank(message = "昵称不能为空")
    String nickName;
    Gender gender;
    String province;
    Date year;
    String avatar;
}
