package com.love.iLove;

import com.alibaba.fastjson.JSONObject;
import com.love.iLove.domain.Message;
import com.love.iLove.domain.MessageText;
import com.love.iLove.domain.User;
import com.love.iLove.enums.MessageStatusEnum;
import com.love.iLove.enums.MessageTextSendTypeEnum;
import com.love.iLove.enums.MessageTypeEnum;
import com.love.iLove.service.MessageService;
import com.love.iLove.service.MessageTextService;
import com.love.iLove.service.UserRoleService;
import com.love.iLove.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.love.iLove.constants.UrlConstants.ADD_USER_DETAIL_URL;

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
		messageText.setMessageType(MessageTypeEnum.WRITEUSERDETAIL);
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
		u = userService.get(u);
		System.out.println("u:"+JSONObject.toJSONString(u));
	}

	@Test
	public void registTest(){

		userRoleService.regist("3","123456");
	}

}