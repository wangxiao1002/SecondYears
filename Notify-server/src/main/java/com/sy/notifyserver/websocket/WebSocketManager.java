package com.sy.notifyserver.websocket;

import javax.websocket.Session;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * webSocket 管理类
 * @author wangxiao
 * @since
 */
public class WebSocketManager {

    /**
     * 聊天session
     */
    private static ConcurrentHashMap<String, LinkedList<Session>> CHAT_SESSION;
    /**
     * 聊天人数
     */
    private static ConcurrentHashMap<String,Integer> CHAT_COUNT;


    static {
        CHAT_SESSION = new ConcurrentHashMap<>(16);
        CHAT_COUNT = new ConcurrentHashMap<>(16);
    }

    /**
     * 添加Session
     * @param chatId key
     * @param session value
     */
    public static void addSession(String chatId,Session session) {

    }

}
