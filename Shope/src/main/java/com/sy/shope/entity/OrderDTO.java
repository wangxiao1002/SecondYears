package com.sy.shope.entity;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * @author: wang xiao
 * @description: 订单DTO
 * @date: Created in 20:34 2020/6/3
 */
@Data
public class OrderDTO {

    @NotEmpty(message = "请选择商品")
    private String spuId;

    @NotEmpty(message = "请选择具体规格要求信息")
    private String ids;

    @Min(value = 0,message = "最少点一个商品")
    private Integer count;

}
