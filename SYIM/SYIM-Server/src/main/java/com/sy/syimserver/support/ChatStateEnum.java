package com.sy.syimserver.support;

/**
 * 发送结果枚举类
 * @author wangxiao
 * @since 1.1
 */
public enum ChatStateEnum {

    /**
     * 成功
     */
    SUCCESS(0,"success"),

    /**
     * 失败
     */
    FAIL(1,"fail")
    ;


    private int code;

    private String message;

    ChatStateEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
