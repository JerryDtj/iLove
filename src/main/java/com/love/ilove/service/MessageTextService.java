package com.love.ilove.service;

import com.love.ilove.domain.MessageText;

import java.util.List;

/**
 * @author dengtianjiao
 */
public interface MessageTextService {
    /**
     * 添加一条消息
     * @param messageText
     * @return
     */
    int add(MessageText messageText);

    /**
     *  查找消息
     * @param messageText
     * @return
     */
    List<MessageText> find(MessageText messageText);
}
