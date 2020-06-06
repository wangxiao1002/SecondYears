package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: wang xiao
 * @description: 产品规格
 * @date: Created in 16:54 2020/6/3
 */
@Data
@TableName("t_shope_spec")
public class Spec {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String specGroupId;

    private BigDecimal price;
}
