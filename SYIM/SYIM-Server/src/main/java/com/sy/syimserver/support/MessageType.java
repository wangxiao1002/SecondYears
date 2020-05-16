package com.sy.syimserver.support;

/**
 * 消息类型
 * @author wangxiao
 * @since 1.1
 */
public enum MessageType {

    /**
     * 文本类型
     */
    TEXT (0,"text"),
    /**
     * 图片
     */
    IMAGE(1,"image"),
    /**
     * 表情
     */
    EXPRESSION(3,"expression")
    ;

    MessageType(int code, String value) {
        this.code = code;
        this.value = value;
    }

    private int code;

    private String value;

}
