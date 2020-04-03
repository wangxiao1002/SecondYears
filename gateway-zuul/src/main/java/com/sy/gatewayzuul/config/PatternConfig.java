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
@PropertySource("classpath:pattern.properties")
@ConfigurationProperties(prefix = "pattern")
public class PatternConfig {


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


    public void setPatternList(List<String> patternList) {
        this.patternList = patternList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }
}
