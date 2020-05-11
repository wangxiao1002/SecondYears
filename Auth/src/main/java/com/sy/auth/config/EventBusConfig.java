package com.sy.auth.config;

import com.google.common.eventbus.AsyncEventBus;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;


/**
 * EventBusConfig
 * @author wangxiao
 * @since 1.1
 */
@Configuration
public class EventBusConfig {

    @Bean
    public EventBus eventBus () {
        return new AsyncEventBus(executor());
    }
    private ThreadPoolExecutor executor(){
        return new ThreadPoolExecutor(3, 2,
                10L, TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(),
                new MailThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
    }


}

