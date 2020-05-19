package com.sy.syimserver.core;

import com.sy.basis.util.CollectionUtil;
import com.sy.syimserver.support.ChatStateEnum;
import com.sy.syimserver.support.ChatType;
import com.sy.syimserver.support.Message;
import com.sy.syimserver.support.MessageType;


import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 发送消息任务
 * @author wangxiao
 * @since 1.1
 */
public class SendTask implements Callable<String> {

    private List<Session> sessionList;

    private String message;

    private String chatId;

    private String authId;

    @Override
    public String call() throws Exception {
        if (CollectionUtil.isEmpty(sessionList)) {
            return ChatStateEnum.FAIL.name();
        }
        for (Session session : sessionList) {
            if (! session.isOpen()) {
                ChatManager.removeSession(chatId,session);
            }

            Message messageDto = Message.build(authId,chatId)
                    .chatType(ChatType.GROUP)
                    .msgType(MessageType.TEXT)
                    .content(message);
            session.getBasicRemote().sendObject(messageDto);
        }
       return ChatStateEnum.SUCCESS.name();
    }


    public SendTask(List<Session> sessionList, String message, String chatId, String authId) {
        this.sessionList = sessionList;
        this.message = message;
        this.chatId = chatId;
        this.authId = authId;
    }
}
