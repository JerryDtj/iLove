package com.love.ilove.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author Jerry
 */
@Getter
public enum MessageTypeEnum {
    /**
     * 消息类别
     */
    WHITETAIL(0,"用户填写详情消息");

    @EnumValue
    private int key;
    private String value;
    private MessageTypeEnum(int key,String value){
        this.key = key;
        this.value = value;
    }


}
