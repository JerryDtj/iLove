package com.love.ilove;

import com.alibaba.fastjson.JSONObject;
import com.love.ilove.domain.Message;
import com.love.ilove.domain.MessageText;
import com.love.ilove.domain.User;
import com.love.ilove.enums.MessageStatusEnum;
import com.love.ilove.enums.MessageTextSendTypeEnum;
import com.love.ilove.enums.MessageTypeEnum;
import com.love.ilove.service.MessageService;
import com.love.ilove.service.MessageTextService;
import com.love.ilove.service.UserRoleService;
import com.love.ilove.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.love.ilove.constants.UrlConstants.ADD_USER_DETAIL_URL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisPlusTests {
	@Autowired
	MessageService messageService;
	@Autowired
	MessageTextService messageTextService;
	@Autowired
	UserService userService;
	@Autowired
	UserRoleService userRoleService;

	@Test
	public void jsonTest() {
		Map map = new HashMap();
		map.put("1","2");
		map.put("123",4);
//		map.put(5,"ceshi");
//		map.put(6,"测试");

		JSONObject jsonObject = new JSONObject(map);
		Message message = new Message(11,MessageStatusEnum.UNREADER,new Date(),333);
		int count = messageService.add(message);
		System.out.println(count+"***"+message.getId());
	}

	@Test
	public void queryTest(){
		Message message = new Message();
		message.setId(8);
		List<Message> list = messageService.find(message);
		System.out.println("list.size="+list.size());
	}

	@Test
	public void insertTest(){
		MessageText messageText = new MessageText();
		messageText.setCreatorName("测试用户");
		messageText.setMessageType(MessageTypeEnum.WHITETAIL);
		messageText.setSendType(MessageTextSendTypeEnum.CONSUMER.CONSUMER);
		messageText.setTitle("系统提示");
		messageText.setContent("请尽快补充完成用户信息");
		messageText.setCreateAt(new Date());
		messageText.setLink(ADD_USER_DETAIL_URL);
		int count = messageTextService.add(messageText);
	}

	@Test
	public void manyToManyTest(){
		User user = userService.getUserRoleByUserName("2");
		System.out.println(JSONObject.toJSONString(user));

		User u = new User();
		u.setUsername("2");
		u.setEnabled(true);
		u.setAccountNonExpired(true);
		u.setAccountNonLocked(true);
		u = userService.get(u);
		System.out.println("u:"+JSONObject.toJSONString(u));
	}


}