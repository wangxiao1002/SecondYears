package com.sy.gatewayzuul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangxiao
 * @description: //认证路由配置类
 * @date 2020/3/29
 */

@Component
@PropertySource(value = "classpath:permission.yml")
@ConfigurationProperties(prefix = "permission")
public class PermissionConfig {


    private List<String> patternList ;

    private List<String> whiteList;

    private List<String> blackList;


    public List<String> getPatternList() {
        return patternList;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public List<String> getBlackList() {
        return blackList;
    }
}
