package com.sy.notifyserver.domain;

/**
 * 基础消息 copy 来源https://www.jianshu.com/p/c773f5a01760
 * @author wangxiao
 * @since 1.1
 */
public class BasicMessage {
    /**
     * 开发者微信号
     */
    private String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private long CreateTime;
    /**
     * 消息类型（text/image/location/link）
     */
    private String MsgType;
    /**
     * 消息id，64位整型
     */
    private long MsgId;
    /**
     * 位0x0001被标志时，星标刚收到的消息
     */
    private int FuncFlag;



    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int funcFlag) {
        FuncFlag = funcFlag;
    }
}
