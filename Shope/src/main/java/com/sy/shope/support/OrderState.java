package com.sy.shope.support;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author: wang xiao
 * @description: 订单状态
 * @date: Created in 11:00 2020/6/4
 */
public enum OrderState implements IEnum<Integer> {

    /**
     * 取消
     */
    CANCEL(-1,"取消"),
    /**
     * 待支付
     */
    WAIT_PAY(0,"待支付"),
    /**
     * 已完成
     */
    DONE(1,"支付完成,待取货");

    private int value;
    private String desc;

    OrderState(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
