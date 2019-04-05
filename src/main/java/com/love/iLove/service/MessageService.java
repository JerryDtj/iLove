package com.love.iLove.service;

import com.love.iLove.domain.Message;
import com.love.iLove.enums.MessageStatusEnum;

import java.util.List;

public interface MessageService {
    int add(Message message);

    List<Message> find(Message message);

    int update(Message message);

    int create(int messageTextId, MessageStatusEnum unreader, int receiverId);
}
