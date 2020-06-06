package com.sy.shope.service.impl;


import com.sy.shope.entity.OrderInfo;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.service.facade.IPriceService;
import com.sy.shope.service.facade.ISkuService;
import com.sy.shope.support.OrderPriceDTO;
import com.sy.shope.support.OrderingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

/**
 * @author: wang xiao
 * @description: price ServiceImpl
 * @date: Created in 19:05 2020/6/3
 */
@Service
public class PriceServiceImpl implements IPriceService {

    @Autowired
    private ISkuService skuService;

    @Override
    public String getPrice(String spuId, String ids) {
       BigDecimal price = getSkuPrice(spuId, ids);
       return price.setScale(2,4).toString();
    }


    @Override
    public BigDecimal getSkuPrice(String spuId, String ids) {
        BigDecimal result;
        SkuInfo skuInfo = skuService.querySkuBySpuIdAndIds(spuId, ids);
        BigDecimal price = Optional.ofNullable(skuInfo)
                .map(SkuInfo::getPrice)
                .orElseThrow(() ->
                new OrderingException("200","请求规格参数异常，无法正常查询到商品规格"));
        // 优惠价格
        result = Optional.ofNullable(skuInfo.getSalePrice()).isPresent()?skuInfo.getSalePrice():price;
        return result;
    }

    @Override
    public BigDecimal getSkuPrice(SkuInfo skuInfo) {
        BigDecimal result;
        BigDecimal price = Optional.ofNullable(skuInfo)
                .map(SkuInfo::getPrice)
                .orElseThrow(() ->
                        new OrderingException("200","请求规格参数异常，无法正常查询到商品规格"));
        // 优惠价格
        result = Optional.ofNullable(skuInfo.getSalePrice()).isPresent()?skuInfo.getSalePrice():price;
        return result;
    }


    @Override
    public OrderPriceDTO calcOrderPrice(List<OrderInfo> orderInfos) {

        OrderPriceDTO result = new OrderPriceDTO();
        BigDecimal price ;
        /**
         * 总价格
         */
        price = orderInfos.stream()
                .map(e->e.getPrice().multiply(BigDecimal.valueOf(e.getCount())))
                .reduce(BigDecimal::add).get();
        result.setPrice(price);
        /**
         * 预留优惠券 满减
         */
        result.setPayPrice(price.subtract(BigDecimal.ZERO));
        result.initSalePrice();
        /**
         * 每一个商品的价格 按照权重计算
         */
        for (OrderInfo orderInfo : orderInfos) {
            // 比例
            BigDecimal rate = orderInfo.getPrice().divide(result.getPrice(),2,4);
            // 应该优惠价格
            BigDecimal salePrice = orderInfo.getPrice().multiply(rate).setScale(2,4);
            orderInfo.setPayPrice(orderInfo.getPrice().subtract(salePrice));
        }
        result.setOrderInfos(orderInfos);
        return result;

    }
}
