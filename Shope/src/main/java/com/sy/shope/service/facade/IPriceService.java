package com.sy.shope.service.facade;



import com.sy.shope.entity.OrderInfo;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.support.OrderPriceDTO;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author: wang xiao
 * @description: 价格
 * @date: Created in 18:55 2020/6/3
 */
public interface IPriceService {

    /**
     *  获取sku的价格
     * @author wangxiao
     * @date 19:04 2020/6/3
     * @param spuId 商品Id
     * @param ids ids
     * @return java.lang.String
     */
    String getPrice(String spuId,String ids);

    /**
     *  获取sku的价格
     * @author wangxiao
     * @date 19:04 2020/6/3
     * @param spuId 商品Id
     * @param ids ids
     * @return BigDecimal
     */
    BigDecimal getSkuPrice(String spuId,String ids);

    /**
     *  获取sku的价格
     * @author wangxiao
     * @date 19:04 2020/6/3
     * @param skuInfo sku
     * @return BigDecimal
     */
    BigDecimal getSkuPrice(SkuInfo skuInfo);

    /**
     *  获取sku的价格
     * @author wangxiao
     * @date 19:04 2020/6/3
     * @param orderInfos sku
     * @return BigDecimal
     */
    OrderPriceDTO calcOrderPrice(List<OrderInfo> orderInfos);






}