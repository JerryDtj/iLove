package com.love.ilove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: Jerry
 * @Date: 2019-05-22 15:27
 */
@Data
@TableName
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField()
    private String url;

    private String describe;


}
