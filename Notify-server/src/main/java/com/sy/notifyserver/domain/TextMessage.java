package com.sy.notifyserver.domain;

/**
 * 文本消息
 * @author wangxiao
 * @since 1.1
 */
public class TextMessage  extends BasicMessage{
    /**
     * 消息内容
     */
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
