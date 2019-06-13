package com.love.ilove.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * @author Jerry
 * @Date 2019-06-08 15:40
 */
@Component
public class MailUtil {

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${emai.userName}")
    private String userName;
    @Value("${emai.protocol}")
    private String protocol;
    @Value("${emai.host}")
    private String host;
    @Value("${emai.port}")
    private String port;
    @Value("${emai.isAuth}")
    private String isAuth;
    @Value("${emai.usessl}")
    private String usessl;
//    @Value("${emai.fromUser}")
//    private String fromUser;




    public void sendTextMail(String[] toUser,String content,String title) throws MessagingException {
        Properties properties = new Properties();
        // 连接协议
        properties.put("mail.transport.protocol", protocol);
        // 主机名
        properties.put("mail.smtp.host", host);
        // 端口号
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", String.valueOf(isAuth));
        // 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.smtp.ssl.enable", usessl);
        // 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.debug", "true");
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress(userName));
        InternetAddress[] addresses = new InternetAddress[toUser.length];
        for (int i = 0; i < toUser.length; i++) {
            addresses[i] = new InternetAddress(toUser[i]);
        }
        message.setRecipients(Message.RecipientType.TO, addresses);
        // 设置收件人邮箱地址
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("jiao_snow@126.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        //一个收件人
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("jiao_snow@126.com"));
        // 设置邮件标题
        message.setSubject(title);
        // 设置邮件内容
        message.setText(content);
        // 得到邮差对象
        Transport transport = session.getTransport();

        // 连接自己的邮箱账户
        // 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        transport.connect(userName, redisTemplate.opsForValue().get("mail-pwd").toString());
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();

    }

    public static void main(String[] args) {
        System.out.println(new Random().nextInt(999999));
    }


   /* public static void main(String[] args) throws  MessagingException {
        MailUtil.sendTextMail(new String[]{"jiao_snow@126.com"},"content","title");

        Properties properties = new Properties();
        // 连接协议
        properties.put("mail.transport.protocol", "smtp");
        // 主机名
        properties.put("mail.smtp.host", "smtp.exmail.qq.com");
        // 端口号
        properties.put("mail.smtp.port", 465);
        properties.put("mail.smtp.auth", "true");
        // 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.smtp.ssl.enable", "true");
        // 设置是否显示debug信息 true 会在控制台显示相关信息
        properties.put("mail.debug", "true");
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("jerry@mail.tianzijiaozi.top"));
        // 设置收件人邮箱地址
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("jiao_snow@126.com"),new InternetAddress("xxx@qq.com"),new InternetAddress("xxx@qq.com")});
        //一个收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("jiao_snow@126.com"));
        // 设置邮件标题
        message.setSubject("xmqtest");
        // 设置邮件内容
        message.setText("邮件内容邮件内容邮件内容xmqtest");
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        // 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        transport.connect("jerry@mail.tianzijiaozi.top", "Ilove1314");
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }*/
}
