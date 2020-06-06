package com.sy.shope.support;

/**
 * jsonResult
 * @author wangxiao
 * @since 11
 */
public class JsonResult<T> {
    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult<T> code (String code) {
        this.code = code;
        return this;
    }
    public JsonResult<T> message (String message) {
        this.message = message;
        return this;
    }
    public JsonResult<T> data (T data) {
        this.data = data;
        return this;
    }

    private JsonResult () {}

    private static <T> JsonResult<T> build () {
        return new JsonResult<T>();
    }

    public static <T>JsonResult<T> fail (String message) {
        JsonResult<T> jsonResult = build();
        return jsonResult.code("200").message(message);
    }
    public static <T>JsonResult<T> fail (String code,String message) {
        JsonResult<T> jsonResult = build();
        return jsonResult.code(code).message(message);
    }

    public static <T>JsonResult<T> success (T data) {
        JsonResult<T> jsonResult = build();
        return jsonResult.code("200").data(data);

    }
    public static <T>JsonResult<T> success (String message,T data) {
        JsonResult<T> jsonResult = build();
        return jsonResult.code("200").message(message).data(data);
    }
}
