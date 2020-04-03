package com.sy.gatewayzuul.support;

/**
 * @author wangxiao
 * @description: //返回结果
 * @date 2020/3/30
 */
public class BaseResponse<T> {

    private String status;

    private String message;

    private T data;

    private BaseResponse () {}


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResponse status(String status) {
        this.status = status;
        return this;
    }
    public BaseResponse message(String message) {
        this.message = message;
        return this;
    }
    public BaseResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public static BaseResponse build() {
        return new BaseResponse();
    }
}
