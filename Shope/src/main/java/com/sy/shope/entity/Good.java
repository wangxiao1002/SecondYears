package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: wang xiao
 * @description: 商品
 * @date: Created in 13:31 2020/6/3
 */
@Data
@TableName("t_shope_spu")
public class Good {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String spuNameZh;

    private String spuNameEn;

    private BigDecimal price;

    private String imageUrl;

    private String state;

    private String categoryId;

    private String spuDesc;

    private String createTime;

    private String updateTime;
    @TableField(exist = false)
    private List<SpecGroupVO> specGroupVOS;

}
