package com.love.ilove.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Jerry
 * @Date: 2019-05-13 10:30
 *
 */
public class EnumUtil {
    /**
     * 更具枚举value获取枚举类
     * @param clazz 枚举反射
     * @param value 要找的值
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>>T getEnumByValue(Class<T> clazz,String value){
        try {
            T[] t = clazz.getEnumConstants();
            Method method = clazz.getDeclaredMethod("getValue");
            if (t!=null){
                for (T ts:t){
                    if (method.invoke(ts).equals(value)){
                        return ts;
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}

