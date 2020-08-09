package com.sy.gatewaygateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayGatewayApplication.class, args);
    }


    /**
     * 可以自定义转发规则  不在yaml 文件中配置
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator (RouteLocatorBuilder builder) {
        return builder.routes().route("path_route",r->r.path("/csdn").uri("https://www.csdn.com")).build();
    }


}
