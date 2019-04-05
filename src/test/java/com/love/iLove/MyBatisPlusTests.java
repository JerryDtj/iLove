package com.love.iLove;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.love.iLove.domain.Message;
import com.love.iLove.enums.MessageStatusEnum;
import com.love.iLove.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonbTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisPlusTests {
	@Autowired
	MessageService messageService;

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

}