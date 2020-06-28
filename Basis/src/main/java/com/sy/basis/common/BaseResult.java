package com.sy.basis.common;

/**
 * 统一结果
 * @author wangxiao
 * @since 1.1
 */
public final class BaseResult<T> {

    private String status;

    private String message;

    private T result;

    public BaseResult() {
    }

    public BaseResult(String status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public BaseResult<T> status(String status) {
        this.status = status;
        return this;
    }
    public BaseResult<T> message(String message) {
        this.message = message;
        return this;
    }
    public BaseResult<T> result(T result) {
        this.result = result;
        return this;
    }

    private static <T> BaseResult<T> build () {
        return new BaseResult<T>();
    }

    public static <T>BaseResult<T> fail (String message) {
        BaseResult<T> jsonResult = build();
        return jsonResult.status("200").message(message);
    }
    public static <T>BaseResult<T> fail (String code,String message) {
        BaseResult<T> jsonResult = build();
        return jsonResult.status(code).message(message);
    }

    public static <T>BaseResult<T> success (T data) {
        BaseResult<T> jsonResult = build();
        return jsonResult.status("200").result(data);

    }
    public static <T>BaseResult<T> success (String message,T data) {
        BaseResult<T> jsonResult = build();
        return jsonResult.status("200").message(message).result(data);
    }


}
