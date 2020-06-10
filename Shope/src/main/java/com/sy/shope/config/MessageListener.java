package com.sy.shope.config;

import com.sy.shope.support.OrderEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * @author wangxiao
 * @since 1.1
 */
@Component
public class MessageListener {

    @EventListener
    public void processAccountCreatedEvent1(OrderEvent event) {
        // TODO
        System.out.println(event.getOrderId());


    }





}
