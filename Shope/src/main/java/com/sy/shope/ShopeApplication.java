package com.sy.shope;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
  * 启动类
  * @author wangxiao
  **/

@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = {"com.sy.shope.mappers"})
public class ShopeApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ShopeApplication.class, args);
    }

}

