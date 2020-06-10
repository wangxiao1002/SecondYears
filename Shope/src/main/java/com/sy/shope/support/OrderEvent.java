package com.sy.shope.support;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单完成事件
 * @author wangxiao
 * @since 1.1
 */
@Data
public class OrderEvent {

    private String orderId;

    private LocalDateTime createTime;


}
