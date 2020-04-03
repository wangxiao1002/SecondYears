package com.sy.gatewayzuul.config;

import com.sy.gatewayzuul.service.facade.UserFeignService;
import com.sy.gatewayzuul.utils.JwtTokenProvider;
import com.sy.gatewayzuul.zuulfilters.AuthFilter;
import com.sy.gatewayzuul.zuulfilters.ErrorFilter;
import com.sy.gatewayzuul.zuulfilters.LogFilter;
import com.sy.gatewayzuul.zuulfilters.RequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wangxiao
 * @description: //TODO
 * @date 2020/4/4
 */
@Configuration
public class ZuulConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Resource
    private UserFeignService userFeignService;

    @Autowired
    private PatternConfig patternConfig;





    @Bean
    public RequestFilter requestFilter (){
        return new RequestFilter(patternConfig.getPatternList(),jwtTokenProvider);
    }

    @Bean
    public LogFilter logFilter () {
        return new LogFilter(patternConfig.getPatternList(),jwtTokenProvider);
    }

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter(patternConfig.getWhiteList(),userFeignService, patternConfig.getPatternList(),jwtTokenProvider);
    }

    @Bean
    public ErrorFilter errorFilter(){
        return new ErrorFilter();
    }
}
