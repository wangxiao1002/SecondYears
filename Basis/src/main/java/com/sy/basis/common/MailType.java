package com.sy.basis.common;

/**
 * 邮件类型
 * @author wangxiao
 * @since 1.1
 */
public enum MailType {

    NORMAL("正常"),

    HTML("html或者模板");

    private String message;


    MailType(String message) {
        this.message = message;
    }
}
