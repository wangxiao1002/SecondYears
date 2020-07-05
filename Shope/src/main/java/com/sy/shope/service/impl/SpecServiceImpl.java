package com.sy.shope.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroup;
import com.sy.shope.mappers.SpecMapper;
import com.sy.shope.service.facade.ISpecService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author: wang xiao
 * @description: spec serviceImpl
 * @date: Created in 17:13 2020/6/3
 */
@Service
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements ISpecService {



    @Override
    public List<SpecGroup> querySpecGroupByGoodId(String goodId) {
        return baseMapper.selectSpecGroupByGoodId(goodId);
    }

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public boolean saveSpecList(String specGroupId, List<Spec> specs) {
        baseMapper.delete(new LambdaQueryWrapper<Spec>().eq(Spec::getSpecGroupId,specGroupId));
        if (CollectionUtils.isEmpty(specs)) {
            return true;
        }
        specs.forEach(e->e.setSpecGroupId(specGroupId));
        return saveBatch(specs);
    }


    @Override
    public boolean delBySpecGroupIds(List<String> ids) {
        QueryWrapper<Spec> queryWrapper = new QueryWrapper<Spec>();
        for (String groupId : ids) {
            queryWrapper.eq("spec_group_id",groupId);
        }
        return remove(queryWrapper);
    }
}
