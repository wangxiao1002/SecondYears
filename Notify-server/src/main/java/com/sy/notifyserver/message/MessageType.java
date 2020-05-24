package com.sy.notifyserver.message;

/**
 * 消息类型
 * @author wangxiao
 * @since 1.1
 */
public enum MessageType {
    /**
     * 公告
     */
    ANNOUNCEMENT(0,"announcement"),
    /**
     * 私信,消息
     */
    LETTERS(1,"letters")
    ;

    private int code;

    private String value;

    MessageType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
