package com.love.iLove.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum MessageTextSendTypeEnum implements IEnum<Integer> {

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
