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
public enum RoleStatus {
    NORMALITY(0,"正常"),DEl(1,"删除");
    int key;
    String value;
    private static Map<Integer, RoleStatus> roleStatusMap = new HashMap<>();

    static {
        for (RoleStatus roleStatus : roleStatusMap.values()) {
            roleStatusMap.put(roleStatus.getKey(), roleStatus);
        }
    }

}
