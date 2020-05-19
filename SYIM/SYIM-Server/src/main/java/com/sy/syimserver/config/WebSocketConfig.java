package com.sy.syimserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * webSocket config 外置的Tomcat 容器时候需要去除掉
 * @author wangxiao
 * @since 1.1
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpoint () {
        return new ServerEndpointExporter();
    }
}
