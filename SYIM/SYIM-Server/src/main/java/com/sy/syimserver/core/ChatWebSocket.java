package com.sy.syimserver.core;

import com.sy.basis.domain.LoginDO;
import com.sy.syimserver.service.facde.AuthFeignService;
import com.sy.syimserver.support.ChatRunTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Optional;
import java.util.concurrent.FutureTask;

/**
 * 群聊 处理类
 * @author wangxiao
 * @since 1.1
 */
@Component
@ServerEndpoint("/chat/{chatId}/{authId}")
public class ChatWebSocket {


    @Autowired
    private AuthFeignService authFeignService;

    /**
     * 打开连接
     * @param session session
     * @param chatId 聊天室标识
     * @param authId  用户标识
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("chatId") String chatId,
                       @PathParam("authId") String authId) {
        ChatManager.addSession(chatId,session);
        sendWelcome(chatId,authId);
    }


    @OnMessage
    public void onMessage (String message,
                           @PathParam("chatId") String chatId,
                           @PathParam("authId") String authId) {
        sendMessage(message, chatId, authId);
    }

    @OnError
    public void OnError(@PathParam("chatId") String chatId,
                 @PathParam("authId") String authId) {
        sendMessage("系统出现异常了,正在紧急处理！",chatId,authId);
    }

    @OnClose
    public void OnClose (Session session,
                         @PathParam("chatId") String chatId,
                         @PathParam("authId") String authId) {
        ChatManager.removeSession(chatId,session);
        sendMessage("关闭了该聊天室",chatId,authId);
    }


    private void sendWelcome (String chatId,String authId) {
        LoginDO authDo = authFeignService.queryUserByUserCode(authId);
        String label = Optional.ofNullable(authDo).map(LoginDO::getLabel).orElseThrow(() ->new ChatRunTimeException("用户没权限假如"));
        sendMessage(String.format("欢迎用户:%s加入聊天室",label),chatId,authId);
    }

    private void sendMessage (String message,String chatId,String authId) {
        FutureTask futureTask = new FutureTask(new SendTask(ChatManager.getSessions(chatId),message,chatId,authId));
        ChatThreadPoolManager.submit(futureTask);
    }
}
