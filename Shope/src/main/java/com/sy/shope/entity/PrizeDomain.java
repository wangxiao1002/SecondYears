package com.sy.shope.entity;



/**
 * 奖品
 * @author wangxiao
 * @since 1.1
 */
public class PrizeDomain {

    private String code;

    private String label;

    private PrizeType type;

    /**
     * 抽中权重
     */
    private int probability;

    /**
     * 库存
     */
    private int count;


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

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "PrizeDomain{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", type=" + type +
                ", probability=" + probability +
                ", count=" + count +
                '}';
    }
}
