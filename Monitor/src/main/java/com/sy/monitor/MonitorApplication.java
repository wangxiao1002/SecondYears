package com.sy.monitor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.util.concurrent.*;

/**
  * @Author wangxiao
  **/
@SpringBootApplication
public class MonitorApplication    {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }
}
