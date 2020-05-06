package com.sy.monitor;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * 监控服务端
  * @author  wangxiao
  **/
@EnableEurekaClient
@EnableAdminServer
@SpringBootApplication
public class MonitorApplication    {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
