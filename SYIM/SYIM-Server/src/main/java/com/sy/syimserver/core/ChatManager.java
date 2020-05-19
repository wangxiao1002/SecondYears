package com.sy.syimserver.core;



import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天管理类
 * @author wangxiao
 * @since 1.1
 */
public class ChatManager {


    private ChatManager () {
    }

    /**
     * 人员Session
     */
    private static ConcurrentHashMap<String, List<Session>> CHAT_SESSIONS ;

    /**
     * 聊天人数
     */
    private static ConcurrentHashMap<String,Integer> CHAT_NUMBER;

    static {
        System.out.println("ChatManager class is init");
        CHAT_SESSIONS = new ConcurrentHashMap<>();
        CHAT_NUMBER = new ConcurrentHashMap<>();
    }

    /**
     * 添加连接
     * @param chatId 群聊ID
     * @param session WebSocketSession
     */
    protected static void addSession (String chatId,Session session) {
        /**
         * 操作连接列表
         */
        List<Session> sessionList = CHAT_SESSIONS.computeIfAbsent(chatId,k-> new ArrayList<>());
        sessionList.add(session);
        /**
         * 操作人数
         */
        CHAT_NUMBER.compute(chatId,(k,v) ->{
            if (null != v) {
                v+=1;
            }else {
                v = 0;
            }
            return v;
        });
    }


    /**
     * 移除
     * @param chatId 群聊ID
     * @param session WebSocketSession
     */
    protected static void removeSession (String chatId,Session session) {
        /**
         * 操作连接列表
         */
        CHAT_SESSIONS.get(chatId).remove(session);

        /**
         * 操作人数
         */
        CHAT_NUMBER.computeIfPresent(chatId,(k,v) -> v-1);
    }



}
