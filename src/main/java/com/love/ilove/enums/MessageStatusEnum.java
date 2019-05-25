package com.love.ilove.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Jerry
 * @Date: 2019-04-03 09:38
 */
public enum MessageStatusEnum {
    /**
     * 消息状态
     */
    UNREADER(0,"未读"),READER(1,"已读");

    @EnumValue
    private int key;
    private String value;
    private static Map<Integer, MessageStatusEnum> messageStatusEnumMap = new HashMap<>();

    static {
        for (MessageStatusEnum messageStatusEnum : MessageStatusEnum.values()) {
            messageStatusEnumMap.put(messageStatusEnum.getKey(), messageStatusEnum);
        }
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private MessageStatusEnum(int key, String value){
        this.key = key;
        this.value = value;
    }

    public static MessageStatusEnum getMessageStatusEnumByKey(int key){
        return messageStatusEnumMap.get(key);
    }

}
