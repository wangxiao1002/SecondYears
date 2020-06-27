package com.sy.shope.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 奖品
 * @author wangxiao
 * @since 1.1
 */
@Data
@TableName("t_shope_prize_domain")
public class PrizeDomain {

    @TableId(value = "id")
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

}
