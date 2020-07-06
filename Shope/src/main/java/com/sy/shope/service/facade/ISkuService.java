package com.sy.shope.service.facade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.Good;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroup;

import java.util.List;


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


    /**
     * 根据规格 全排列sku信息
     * @param good 商品
     * @return skuInfos
     */
    List<SkuInfo> initSkuInfo (Good good);

}
