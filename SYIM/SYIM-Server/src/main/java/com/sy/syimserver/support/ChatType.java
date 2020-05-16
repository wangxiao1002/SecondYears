package com.sy.syimserver.support;

/**
 * 聊天类型
 * @author wangxiao
 * @since 1.1
 */
public enum ChatType {

    /**
     * 私聊
     */
    PRIVATE(0,"private"),
    /**
     * 群聊
     */
    GROUP(1,"group")
    ;

    private int code;

    private String value;

    ChatType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
