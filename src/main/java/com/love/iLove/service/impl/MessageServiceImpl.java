package com.love.iLove.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.love.iLove.domain.Message;
import com.love.iLove.enums.MessageStatusEnum;
import com.love.iLove.mapper.MessageMapper;
import com.love.iLove.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @auther: Jerry
 * @Date: 2019-04-03 10:46
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public int add(Message message) {
       return messageMapper.insert(message);
    }

    @Override
    public List<Message> find(Message message) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntity(message);
        return messageMapper.selectList(queryWrapper);
    }

    @Override
    public int update(Message message) {
        return messageMapper.updateById(message);
    }

    @Override
    public int create(int messageTextId, MessageStatusEnum unreader, int receiverId) {
        Message message = new Message();
        message.setMessageTextId(messageTextId);
        message.setCreateAt(new Date());
        message.setStatus(unreader);
        message.setReceiverId(receiverId);
        return this.add(message);
    }
}
