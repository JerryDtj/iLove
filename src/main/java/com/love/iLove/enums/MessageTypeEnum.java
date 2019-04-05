package com.love.iLove.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum MessageTypeEnum {
    WRITEUSERDETAIL(0,"用户填写详情消息");

    @EnumValue
    private int key;
    private String value;
    private MessageTypeEnum(int key,String value){
        this.key = key;
        this.value = value;
    }


}
