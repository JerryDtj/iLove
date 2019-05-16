package com.love.iLove.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: Jerry
 * @Date: 2019-04-18 17:47
 */
@AllArgsConstructor
@Getter
public enum Gender {
    WOMAN(0,"女"),MAN(1,"男");
    int key;
    String value;
    private static Map<Integer, Gender> genderMap = new HashMap<>();

    static {
        for (Gender gender : genderMap.values()) {
            genderMap.put(gender.getKey(), gender);
        }
    }

}
