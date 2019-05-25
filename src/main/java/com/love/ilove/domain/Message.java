package com.love.ilove.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.love.ilove.enums.MessageStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author: Jerry
 * @Date: 2019-04-03 10:00
 */
@Data
@TableName
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 接收者用户/组Id
     */
    private Integer receiverId;
    /**
     * 阅读状态：已读，未读
     */
    private MessageStatusEnum status;
    /**
     * 接收时间
     */
    private Date createAt;
    /**
     * 消息内容Id
     */
    private Integer messageTextId;

    public Message(int receiverId, MessageStatusEnum status, Date createAt, int messageTextId){
        this.receiverId = receiverId;
        this.status = status;
        this.createAt = createAt;
        this.messageTextId = messageTextId;
    }
}
