package com.love.ilove.service;

import com.love.ilove.domain.Message;
import com.love.ilove.enums.MessageStatusEnum;

import java.util.List;

/**
 * @author dengtianjiao
 */
public interface MessageService {
    /**
     * 添加一条消息
     * @param message
     * @return
     */
    int add(Message message);

    /**
     * 查找一条消息
     * @param message
     * @return
     */
    List<Message> find(Message message);

    /**
     * 更新一条消息
     * @param message
     * @return
     */
    int update(Message message);

    /**
     * 创建一条消息
     * @param messageTextId
     * @param unreader
     * @param receiverId
     * @return
     */
    int create(int messageTextId, MessageStatusEnum unreader, int receiverId);
}
