package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.shope.support.OrderState;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: wang xiao
 * @description: 订单
 * @date: Created in 20:37 2020/6/3
 */
@Data
@TableName("t_shope_order")
public class Order {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String userId;

    private BigDecimal price;

    private BigDecimal payPrice;

    private OrderState state;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private int descNo;

    private String paper;

    private String milk;

    private String sugar;

    private String remark;

    @TableField(exist = false)
    private List<OrderInfo> orderInfos;
}
