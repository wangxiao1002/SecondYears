package com.sy.shope.support;

import com.sy.shope.entity.OrderInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wang xiao
 * @description: 计算订单价格实体类
 * @date: Created in 12:48 2020/6/4
 */
@Data
public class OrderPriceDTO {
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 实际支付
     */
    private BigDecimal payPrice;
    /**
     * 优惠了的价格（价格 - 实际价格）
     */
    private BigDecimal salePrice;

    private List<OrderInfo> orderInfos;

    public void initSalePrice () {
        this.salePrice = this.price.subtract(payPrice);
    }

}
