package com.love.ilove.security.service;

import com.love.ilove.domain.User;
import com.love.ilove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userService.getUserRoleByUserName(username);
        if (user==null){
            throw new RuntimeException("user is null");
        }

        //检测用户详情是否为null，为null，系统默认发一条消息给客户
        /*Gender userDetail = userDetailService.getDetilById(user.getId());
        if (userDetail==null){
            MessageText messageText = new MessageText();
            messageText.setCreatorName("sysAdmin");
            messageText.setMessageType(MessageTypeEnum.WHITETAIL);
            messageText.setSendType(MessageTextSendTypeEnum.CONSUMER.CONSUMER);
            List<MessageText> text = messageTextService.find(messageText);

            if (text.size()==0){//没有消息，创建消息主体和消息发送对象
                messageText.setTitle("系统提示");
                messageText.setContent("请尽快补充完成用户信息");
                messageText.setCreateAt(new Date());
                messageText.setLink(ADD_USER_DETAIL_URL);
                int count = messageTextService.add(messageText);
                if (count>0){
                    //创建消息发送对象
                    Message message = new Message(user.getId(),MessageStatusEnum.UNREADER,new Date(),messageText.getId());
                    int num = messageService.add(message);
                    if (num<=0){
                        //todo 自定义异常封装
                        throw new RuntimeException("创建失败");
                    }
                }
            }else {//已经有消息主体，检查是否已读，如果已读，改变阅读状态
                Message message = new Message();
                int messageTextId = text.get(0).getId();
                message.setMessageTextId(messageTextId);
                List<Message> messageList = messageService.find(message);
                if (messageList.size()==0){
                    //消息为空，创建消息发送对象
                    message = new Message(user.getId(),MessageStatusEnum.UNREADER,new Date(),messageTextId);
                    int messageId = messageService.add(message);
                    if (messageId<=0){
                        //todo 自定义异常封装
                        throw new RuntimeException("创建失败");
                    }
                }else {
                    message = messageList.get(0);
                    message.setStatus(MessageStatusEnum.UNREADER);
                    int num = messageService.update(message);
                    if (num<=0){
                        //todo 自定义异常封装
                        throw new RuntimeException("创建失败");
                    }
                }
            }
        }*/
        return user;

    }

    private List<SimpleGrantedAuthority> getCrantedAuthority(String roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        String[] role = roles.split(",");
        for (String s:role){
            list.add(new SimpleGrantedAuthority(s));
        }
        return list;
    }
}
