package com.love.iLove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.iLove.enums.MessageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @auther: Jerry
 * @Date: 2019-04-03 10:00
 */
@Data
@TableName
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer receiverId;//接收者用户/组Id

    private MessageStatusEnum status;//阅读等状态

    private Date createAt;//接收时间

    private Integer messageTextId;//消息内容Id

    public Message(int receiverId, MessageStatusEnum status, Date createAt, int messageTextId){
        this.receiverId = receiverId;
        this.status = status;
        this.createAt = createAt;
        this.messageTextId = messageTextId;
    }
}
