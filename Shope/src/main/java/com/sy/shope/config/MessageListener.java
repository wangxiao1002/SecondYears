package com.sy.shope.config;

import com.sy.shope.support.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息监听器
 * @author wangxiao
 * @since 1.1
 */
@Slf4j
@Component
public class MessageListener  {


    @EventListener
    @Async(value = "asyncExecutor")
    public void successOrder(OrderEvent event) {
        // TODO
        log.info("订单完成支付====>id:{},开始发送通知",event.getOrderId());
    }





}
