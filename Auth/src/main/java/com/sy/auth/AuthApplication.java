package com.sy.auth;

import com.sy.auth.mapper.AuthorityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 用户认证授权，配置等服务类
 * @author wangxiao
 * @date 2020-04-30
 */
@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

    @Autowired
    private AuthorityMapper authorityMapper;

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        authorityMapper.insertUserAuthority("00","22","33");
    }
}
