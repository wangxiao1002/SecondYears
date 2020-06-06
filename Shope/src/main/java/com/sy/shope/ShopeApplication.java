package com.sy.shope;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

 /**
  * 启动类
  * @author wangxiao
  **/
@SpringBootApplication
@MapperScan(value = {"com.sy.shope.mappers"})
public class ShopeApplication  {

    public static void main(String[] args) {
        SpringApplication.run(ShopeApplication.class, args);
    }

}

