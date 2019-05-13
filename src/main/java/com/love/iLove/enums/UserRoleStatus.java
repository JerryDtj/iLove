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
public enum UserRoleStatus {
    NORMALITY(0,"正常"),DEl(1,"删除");
    int key;
    String value;
    private static Map<Integer, UserRoleStatus> userRoleStatusMap = new HashMap<>();

    static {
        for (UserRoleStatus userRoleStatus : userRoleStatusMap.values()) {
            userRoleStatusMap.put(userRoleStatus.getKey(), userRoleStatus);
        }
    }

}
