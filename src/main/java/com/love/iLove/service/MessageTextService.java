package com.love.iLove.service;

import com.love.iLove.domain.MessageText;

import java.util.List;

public interface MessageTextService {
    int add(MessageText messageText);

    List<MessageText> find(MessageText messageText);
}
