package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.MessageText;
import com.love.iLove.mapper.MessageTextMapper;
import com.love.iLove.service.MessageTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @auther: Jerry
 * @Date: 2019-04-03 10:47
 */
@Service("messageText")
public class MessageTextServiceImpl implements MessageTextService {
    @Autowired
    MessageTextMapper messageTextMapper;

    @Override
    public int add(MessageText messageText) {
        return messageTextMapper.insert(messageText);
    }

    @Override
    public List<MessageText> find(MessageText messageText) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.setEntity(messageText);
       return messageTextMapper.selectList(wrapper);
    }
}
