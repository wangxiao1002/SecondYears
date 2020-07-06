package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.shope.tools.Constants;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    private boolean enable;

    private String createTime;

    private String updateTime;


    public void  copyProperties (Good good) {
        this.title = good.getSpuNameZh();
        this.enable = true;
        this.indexes = null;
        this.images = good.getImageUrl();
        this.price = good.getPrice();
        this.createTime = LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER);
    }
}
