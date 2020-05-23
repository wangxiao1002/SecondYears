package com.sy.notifyserver.websocket;

import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
        LinkedList<Session> sessions = CHAT_SESSION.computeIfAbsent(chatId,key ->new LinkedList<>());
        sessions.add(session);
        CHAT_COUNT.compute(chatId,(key,value) -> {
            if (null == value) {
                value = 0;
            }else {
                value += 1;
            }
            return value;
        });
    }

    /**
     * 移除session
     * @param chatId
     * @param session
     */
    public static void removeSession (String chatId,Session session) {
        AtomicInteger sessionSize = new AtomicInteger();
        CHAT_SESSION.computeIfPresent(chatId,(key,value) -> {
            value.remove(session);
            sessionSize.set(value.size());
            return value;
        });
        if (sessionSize.get() == 0 ) {
            CHAT_SESSION.remove(chatId);
        }
        AtomicInteger countSize = new AtomicInteger();
        CHAT_COUNT.computeIfPresent(chatId,(key,value) -> {
             value -= 1;
             countSize.set(value);
             return value;
        });
        if (countSize.get() == 0 ) {
            CHAT_COUNT.remove(chatId);
        }
    }

    public static int count (String chatId) {
        return CHAT_COUNT.get(chatId);
    }

    public static List<Session> session (String chatId) {
        return CHAT_SESSION.get(chatId);
    }
}
