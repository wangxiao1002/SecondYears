package com.sy.shope.service.facade;

/**
 * SocketIoService
 * @author wangxiao
 * @since 1.1
 */
public interface SocketIOService {

    /**
     * 启动
     */
    void start();

    /**
     * 停止
     */
    void stop();

    /**
     * 给目标发送消息
     * @param userId
     * @param msg
     */
    void pushTargetMessage(String userId, String msg);


    /**
     * 群发
     * @param msg
     */
    void pushMessageToAll(String msg);
}
