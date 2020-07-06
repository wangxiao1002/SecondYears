package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Good;
import com.sy.shope.entity.SkuInfo;

import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroup;
import com.sy.shope.mappers.SkuMapper;


import com.sy.shope.service.facade.ISkuService;
import com.sy.shope.tools.Constants;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author: wang xiao
 * @description: sku ServiceImpl
 * @date: Created in 19:15 2020/6/3
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, SkuInfo> implements ISkuService {

    @Override
    public SkuInfo querySkuBySpuIdAndIds(String spuId, String ids) {
        return baseMapper.selectOne(Wrappers.<SkuInfo>lambdaQuery()
                .eq(SkuInfo::getIndexes,ids)
                .eq(SkuInfo::getSpuId,spuId)
                .eq(SkuInfo::isEnable,true));
    }


    @Override
    public boolean initSkuInfo(Good good) {
        deleteSkuBySpuId(good.getId());
        List<SkuInfo> skuInfos = new ArrayList<>();
        if (CollectionUtils.isEmpty(good.getSpecGroups())) {
            SkuInfo oneInfo = new SkuInfo();
            oneInfo.copyProperties(good);
            skuInfos.add(oneInfo);
            return saveBatch(skuInfos);
        }
        List<List<Spec>> specList = good.getSpecGroups().stream().map(SpecGroup::getSpecList).collect(Collectors.toList());
        descartesRecursive(good.getId(),specList,0,skuInfos,new ArrayList<Spec>());
        return saveBatch(skuInfos);
    }


    @Override
    public Page<SkuInfo> queryByPage(int page, int pageSize, String goodId) {
        if (StringUtils.isEmpty(goodId)) {
            return page(new Page<>(page,pageSize),Wrappers.<SkuInfo>lambdaQuery().orderByDesc(SkuInfo::getCreateTime));
        }
        return page(new Page<>(page,pageSize),Wrappers.<SkuInfo>lambdaQuery().orderByDesc(SkuInfo::getCreateTime)
            .eq(SkuInfo::getSpuId,goodId));

    }

    private boolean deleteSkuBySpuId (String spuId) {
        return remove(Wrappers.<SkuInfo>lambdaQuery().eq(SkuInfo::getSpuId,spuId));
    }


    /**
     * 笛卡尔积全排列
     * @param spuId
     * @param originalList
     * @param position
     * @param returnList
     * @param cacheList
     */
    private  void descartesRecursive(String spuId,List<List<Spec>> originalList, int position, List<SkuInfo> returnList, List<Spec> cacheList) {
        List<Spec> originalItemList = originalList.get(position);
        for (int i = 0; i < originalItemList.size(); i++) {
            List<Spec> childCacheList = (i == originalItemList.size() - 1) ? cacheList : new ArrayList<Spec>(cacheList);
            childCacheList.add(originalItemList.get(i));
            if (position == originalList.size() - 1) {
                returnList.add(initSkuInfo(spuId,childCacheList));
                continue;
            }
            descartesRecursive(spuId,originalList, position + 1, returnList, childCacheList);
        }
    }



    private  SkuInfo initSkuInfo (String spuId,List<Spec> specs) {
        String title = specs.stream().map(Spec::getName).collect(Collectors.joining("+"));
        BigDecimal price = specs.stream().map(Spec::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        String ids = specs.stream().map(Spec::getId).collect(Collectors.joining(","));
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        skuInfo.setCreateTime(LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
        skuInfo.setTitle(title);
        skuInfo.setPrice(price);
        skuInfo.setEnable(true);
        skuInfo.setIndexes(ids);
        return skuInfo;
    }





}
