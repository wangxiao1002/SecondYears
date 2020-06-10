package com.sy.shope.support;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 订单完成事件
 * @author wangxiao
 * @since 1.1
 */
@Setter
@Getter
public class OrderEvent {

    private String orderId;

    private LocalDateTime createTime;

    public OrderEvent(String orderId) {
        this.orderId = orderId;
        this.createTime = LocalDateTime.now();
    }
}
