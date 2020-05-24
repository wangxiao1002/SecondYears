package com.sy.notifyserver.message;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

/**
 * socket io 监听
 * @author wangxiao
 * @since 1.1
 */
@Component
public class MessageEventListener  {

    /**
     * 客户端连接
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        System.out.println("建立连接");
    }
    /**
     * 客户端断开
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId = client.getHandshakeData().getSingleUrlParam("userId");
        System.out.println("关闭连接");

    }



    @OnEvent("messageEvent")
    public void onEvent(SocketIOClient client, AckRequest request) {
    }
}
