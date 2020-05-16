package com.sy.syimserver.support;

import com.sy.syimserver.util.DateUtil;
import java.time.LocalDateTime;


/**
 * 消息实体
 * @author wangxiao
 * @since 1.1
 */
public class Message {



    /**
     * 来源
     */
    private String from;

    /**
     * 目标
     */
    private String to;

    /**
     * 消息类型
     */
    private MessageType msgType;

    /**
     * 聊天类型
     */
    private ChatType chatType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    private String createTime;


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public MessageType getMsgType() {
        return msgType;
    }

    public void setMsgType(MessageType msgType) {
        this.msgType = msgType;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    private Message(String from, String to) {
        this.from = from;
        this.to = to;
        this.createTime = DateUtil.formatDateTime(LocalDateTime.now());
    }

    public static Message build (String from,String to) {
        return new Message(from, to);
    }


    public Message msgType (MessageType msgType) {
        this.msgType = msgType;
        return this;
    }

    public Message chatType (ChatType chatType) {
        this.chatType = chatType;
        return this;
    }

    public Message content (String content) {
        this.content = content;
        return this;
    }


}
