package com.sy.servercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication

/**
 * @Author wangxiao
 * @Description // EurekaServer
 **/

public class ServerCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerCenterApplication.class, args);
    }

}
