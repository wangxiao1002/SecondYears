package com.sy.notifyserver.support;

/**
 * jsonResult
 * @author wangxiao
 * @since 1.1
 */
public class JsonResult<T> {

    private int code;

    private String message;

    private T value;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    private JsonResult (int code) {
        this.code = code;
    }

    public static<T> JsonResult<T> build (int code) {
        return new JsonResult<T>(code);
    }

    public JsonResult<T> value (T value) {
        this.value = value;
        return this;
    }

    public JsonResult<T> message(String message) {
        this.message = message;
        return this;
    }

}
