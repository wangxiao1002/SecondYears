package com.sy.notifyserver.websocket;

import com.sy.notifyserver.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;

/**
 * 聊天室webSocket
 * @author wangxiao
 * @since 1.1
 */
@Component
@ServerEndpoint("/chat/{chatId}")
public class ChatWebSocket {

    private Logger logger = LoggerFactory.getLogger(ChatWebSocket.class);

    @OnOpen
    public void onOpen (Session session, @PathParam("chatId") String chatId) {
        WebSocketManager.addSession(chatId,session);
        logger.info("聊天ID: {}有新的人加入,当前在线人数:{}",chatId,WebSocketManager.count(chatId));
        sendMessage(chatId,SocketMessage.build().fromId("").message("欢迎加入"));
    }

    @OnMessage
    public void onMessage (@PathParam("chatId") String chatId,String message) {
        logger.info("聊天ID: {}接收到消息",chatId);
        sendMessage(chatId,SocketMessage.build().fromId("").message(message));
    }

    @OnClose
    public void onClose(@PathParam("chatId") String chatId,Session session) {
        logger.info("聊天ID: {}关闭",chatId);
    }

    @OnError
    public void onError (@PathParam("chatId") String chatId,Session session,Throwable error) {
        logger.info("聊天ID: {},聊天异常: {}",chatId,error.getMessage());
    }

    private void  sendMessage (String chatId,SocketMessage message) {
        List<Session>  sessionList = WebSocketManager.session(chatId);

        if (null == sessionList || sessionList.isEmpty()) {
            return;
        }
        // 暂时 不用线程池
        for (Session session : sessionList) {
            session.getAsyncRemote().sendText(JsonUtil.convertString(message));
        }
    }
}
