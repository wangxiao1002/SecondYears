package com.sy.shope.entity;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 奖品类型
 * @author wangxiao
 * @since 1.1
 */
public enum PrizeType implements IEnum<Integer> {

    /**
     * 特定奖
     */
    GRAND(0,"grand"),
    /**
     * 一等奖
     */
    ONE(1,"one"),
    /**
     * 二等奖
     */
    TWO(2,"two"),
    /**
     * 三等奖
     */
    THREE(3,"three"),
    /**
     * 四等奖
     */
    FOUR(4,"four"),
    /**
     * 五等奖
     */
    FIVE(5,"five"),
    /**
     * 幸运奖
     */
    LUCKY(6,"lucky");
    private int code;

    private String value;

    PrizeType(int code, String value) {
        this.code = code;
        this.value = value;
    }



    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
