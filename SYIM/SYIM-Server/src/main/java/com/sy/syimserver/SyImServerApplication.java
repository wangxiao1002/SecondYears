package com.sy.syimserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 服务启动类
 * @author wangxiao
 * @date 2020-5-16
 */
@EnableFeignClients
@SpringBootApplication
public class SyImServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyImServerApplication.class, args);
    }

}
