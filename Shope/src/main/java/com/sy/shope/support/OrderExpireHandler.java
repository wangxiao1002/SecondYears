package com.sy.shope.support;


import com.sy.shope.service.facade.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.DelayQueue;

/**
 * @author: wang xiao
 * @description: 订单超时取消 处理器
 * @date: Created in 14:34 2020/6/4
 */
@Slf4j
@Component
public class OrderExpireHandler implements Runnable{

    private DelayQueue<OrderExpireDTO<String>> queue = new DelayQueue<>();


    private IOrderService orderService;

    @Autowired
    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    public boolean addOrderExpire (OrderExpireDTO<String> orderExpireDTO ) {
        return queue.offer(orderExpireDTO);
    }


    @Override
    public void run() {
        while(true) {
            try {
                OrderExpireDTO<String> expireDTO = queue.take();
                String orderId = expireDTO.getData();
                log.warn("订单警告：================>订单超时取消，订单Id:{}",orderId);
                orderService.cancelOrder(orderId);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

    }


}
