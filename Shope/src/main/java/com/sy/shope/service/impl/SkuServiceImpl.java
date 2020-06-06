package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.mappers.SkuMapper;


import com.sy.shope.service.facade.ISkuService;
import org.springframework.stereotype.Service;

/**
 * @author: wang xiao
 * @description: sku ServiceImpl
 * @date: Created in 19:15 2020/6/3
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuInfo> implements ISkuService {

    @Override
    public SkuInfo querySkuBySpuIdAndIds(String spuId, String ids) {
        QueryWrapper<SkuInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("indexes",ids)
                .eq("spu_id",spuId)
                .eq("enable","1");
        return baseMapper.selectOne(queryWrapper);
    }
}
