package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.iLove.enums.MessageTextSendTypeEnum;
import com.love.iLove.enums.MessageTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: Jerry
 * @Date: 2019-04-03 10:00
 */
@Data
@TableName
public class MessageText implements Serializable {

    @TableId(type = IdType.AUTO)
    Integer id;
    //标题
    String title;
    //内容
    String content;
    //发送时间
    Date createAt;
    //消息类型
    MessageTypeEnum messageType;
    //接收者类型单用户/用户群组
    MessageTextSendTypeEnum sendType;
    //发送者用户名
    String creatorName;
    //详情链接
    String link;


}
