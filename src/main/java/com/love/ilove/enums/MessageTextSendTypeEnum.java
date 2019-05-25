package com.love.ilove.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author Jerry
 */

public enum MessageTextSendTypeEnum implements IEnum<Integer> {
    /**
     * 消息发送对象
     */
    CONSUMER(0,"用户"),GROUP(1,"用户组");

    private int key;
    private String value;

    @Override
    public Integer getValue() {
        return this.key;
    }

    MessageTextSendTypeEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


}
