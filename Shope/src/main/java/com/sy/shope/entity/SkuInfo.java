package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wang xiao
 * @description: sku
 * @date: Created in 19:08 2020/6/3
 */
@Data
@TableName("t_shope_sku")
public class SkuInfo {

    @TableId
    private String id;

    private String title;

    private String spuId;

    private String images;

    private BigDecimal price;

    private BigDecimal salePrice;

    private String indexes;

    private String ownSpec;

    private String enable;

    private String createTime;

    private String updateTime;
}
