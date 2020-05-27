package com.sy.shope.support;

/**
 * jsonResult
 * @author wangxiao
 * @since 11
 */
public class JsonResult {
    private int code;

    private String message;

    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResult code (int code) {
        this.code = code;
        return this;
    }
    public JsonResult message (String message) {
        this.message = message;
        return this;
    }
    public JsonResult data (Object data) {
        this.data = data;
        return this;
    }

    private JsonResult () {}

    private static  JsonResult build () {
        return new JsonResult();
    }

    public static JsonResult fail (String message) {
        return build().code(200).message(message);
    }

    public static JsonResult success (Object data) {
        return build().code(200).data(data);
    }
    public static JsonResult success (String message,Object data) {
        return build().code(200).data(data).message(message);
    }
}
