package com.sy.notifyserver.websocket;

/**
 * 消息类型
 * @author wangxiao
 * @since 1.1
 */
public enum SocketMessageType {

    /**
     * 文本
     */
    TXT(0,"txt"),
    /**
     * 表情
     */
    EXPRESS(1,"expression"),
    /**
     * 图片
     */
    IMAGE(2,"image");


    private int code;

    private String value;


    SocketMessageType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
