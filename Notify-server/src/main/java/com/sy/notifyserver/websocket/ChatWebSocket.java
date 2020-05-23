package com.sy.notifyserver.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * 聊天室webSocket
 * @author wangxiao
 * @since 1.1
 */
@Component
@ServerEndpoint("/chat/{chatId}")
public class ChatWebSocket {

    @OnOpen
    public void onOpen (Session session, @PathParam("chatId") String chatId) {}

    @OnMessage
    public void onMessage (String message) {}

    @OnClose
    public void onClose(Session session) {}

    @OnError
    public void onError (Session session) {}

    private void  sendMessage (String chatId,SocketMessage message) {}
}
