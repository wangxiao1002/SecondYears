package com.sy.shope.service.facade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.SkuInfo;


/**
 * @author: wang xiao
 * @description: sku Service
 * @date: Created in 19:14 2020/6/3
 */
public interface ISkuService extends IService<SkuInfo> {

    /**
     *  根据商品和商品规格查询sku
     * @author wangxiao
     * @date 19:17 2020/6/3
     * @param spuId
     * @param ids
     * @return SkuInfo
     */
    SkuInfo querySkuBySpuIdAndIds(String spuId, String ids);

}
