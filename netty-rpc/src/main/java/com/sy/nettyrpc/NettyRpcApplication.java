package com.sy.nettyrpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于netty 自定义rpc 框架
 */
@SpringBootApplication
public class NettyRpcApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcApplication.class, args);
    }

}
