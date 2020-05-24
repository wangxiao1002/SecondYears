package com.sy.notifyserver.message;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;



/**
 * 消息推送服务
 * @author wangxiao
 * @since 1.1
 */
@Component
public class MessagePushServer implements InitializingBean {

    @Resource
    private MessageEventListener eventListener;

    @Value("${push.server.port}")
    private int serverPort;


    @Override
    public void afterPropertiesSet() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setPort(serverPort);
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        configuration.setSocketConfig(socketConfig);
        configuration.setHostname("localhost");

        SocketIOServer server = new SocketIOServer(configuration);
        server.addListeners(eventListener);
        server.start();
        System.out.println("socket 启动");
    }
}
