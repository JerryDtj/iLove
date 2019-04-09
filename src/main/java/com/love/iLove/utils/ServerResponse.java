package com.love.iLove.utils;

import com.love.iLove.enums.ResponseCode;

import java.io.Serializable;

/**
 * @auther: Jerry
 * @Date: 2019-04-08 08:27
 */
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T>
    createBySuccessMessage(String msg) {
        return new
                ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T>
    createBySuccessCodeMessage(String msg, T data) {
        return new ServerResponse<T>
                (ResponseCode.SUCCESS.getCode(), msg, data)
                ;
    }

    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>
                (ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc())
                ;
    }

    public static <T> ServerResponse<T> createByErrorMessage
            (String errorMessage) {
        return new
                ServerResponse<T>
                (ResponseCode.ERROR.getCode(), errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage
            (int erroCode, String errorMessage) {
        return new ServerResponse<T>(erroCode, errorMessage);
    }
}
