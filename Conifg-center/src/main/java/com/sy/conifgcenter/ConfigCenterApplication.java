package com.sy.conifgcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
  * 基础git 配置中心
  * @author wangxiao
  * @date 2020-4-28
  **/
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class ConfigCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterApplication.class, args);
    }

}
