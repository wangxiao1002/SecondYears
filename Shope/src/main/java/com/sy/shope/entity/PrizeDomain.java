package com.sy.shope.entity;

import java.math.BigDecimal;

/**
 * 奖品
 * @author wangxiao
 * @since 1.1
 */
public class PrizeDomain {


    private String label;

    private PrizeType type;

    private BigDecimal probability;

    private BigDecimal count;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PrizeType getType() {
        return type;
    }

    public void setType(PrizeType type) {
        this.type = type;
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PrizeDomain{" +
                "label='" + label + '\'' +
                ", type=" + type +
                ", probability=" + probability +
                ", count=" + count +
                '}';
    }
}
