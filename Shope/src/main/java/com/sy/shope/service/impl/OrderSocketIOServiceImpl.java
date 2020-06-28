package com.sy.shope.service.impl;


import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.sy.shope.service.facade.OrderSocketIOService;
import io.socket.client.Socket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SocketIOServiceImpl
 * @author wangxiao
 * @since 1.1
 */
@Slf4j
@Service("orderSocketIOService")
public class OrderSocketIOServiceImpl implements OrderSocketIOService {


    private static Map<String, SocketIOClient> CLIENT_MAP = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;


    @Override
    public void start() {

        /**
         * CONNECT
         */
        socketIOServer.addConnectListener(client -> {
            client.sendEvent(Socket.EVENT_CONNECT, "您已经成功链接上");
            String userId = getParamsByClient(client);
            if (userId != null) {
                CLIENT_MAP.put(userId, client);
            }
        });

        /**
         * DISCONNECT
         */
        socketIOServer.addDisconnectListener(client -> {
            String userId = getParamsByClient(client);
            if (userId != null) {
                CLIENT_MAP.remove(userId);
                client.disconnect();
            }
        });

        /**
         * MESSAGE
         */
        socketIOServer.addEventListener(Socket.EVENT_MESSAGE, String.class, (client, data, ackSender) -> {
            String clientIp = getIpByClient(client);
            log.info("从客户端：{} 接收到数据 data: {}",clientIp,data);
        });

        /**
         * ping
         */
        socketIOServer.addEventListener(Socket.EVENT_PING, String.class, (client, data, ackSender) -> {
            client.sendEvent(Socket.EVENT_PONG,"pong");
        });

        socketIOServer.start();


    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }

    @PostConstruct
    private void autoStartup() {
        start();
    }


    @PreDestroy
    private void autoStop() {
        stop();
    }

    @Override
    public void pushTargetMessage(String userId, String msg) {
        SocketIOClient client = CLIENT_MAP.get(userId);
        if (client != null) {
            client.sendEvent(Socket.EVENT_MESSAGE, msg);
        }

    }

    @Override
    public void pushMessageToAll(String msg) {
        socketIOServer.getBroadcastOperations().sendEvent(Socket.EVENT_MESSAGE, msg);
    }



    /**
     * 获取客户端url中的userId参数（这里根据个人需求和客户端对应修改即可）
     * @param client: 客户端
     * @return java.lang.String
     */
    private String getParamsByClient(SocketIOClient client) {
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> userIdList = params.get("userId");
        if (!CollectionUtils.isEmpty(userIdList)) {
            return userIdList.get(0);
        }
        return null;
    }

    /**
     * 获取连接的客户端ip地址
     * @param client: 客户端
     * @return java.lang.String
     */
    private String getIpByClient(SocketIOClient client) {
        String sa = client.getRemoteAddress().toString();
        return sa.substring(1, sa.indexOf(":"));
    }
}
