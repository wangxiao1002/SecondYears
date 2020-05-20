package com.sy.basis.notify;

/**
 * 消息类型
 * @author wangxiao
 * @since 1.1
 */
public enum NotifyType {

    /**
     * 公告
     */
    ANNOUNCE(0,"公告"),

    /**
     * 提醒
     */
    REMIND(1,"提醒"),

    /**
     * 私信
     */
    MESSAGE(2,"私信");


    private int code;

    private String value;

    NotifyType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
