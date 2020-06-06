package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: wang xiao
 * @description: 订单信息
 * @date: Created in 11:23 2020/6/4
 */
@Data
@TableName("t_shope_order_info")
public class OrderInfo {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String orderId;

    private String spuId;

    private String skuId;

    private BigDecimal price;

    private BigDecimal payPrice;

    private int count;

    private String userId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
