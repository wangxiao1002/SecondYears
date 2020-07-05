package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.SpecGroup;
import com.sy.shope.mappers.SpecGroupMapper;
import com.sy.shope.service.facade.ISpecGroupService;
import com.sy.shope.service.facade.ISpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 * SpecGroupServiceImpl
 * @author wangxiao
 * @since 1.1
 */
@Service
public class SpecGroupServiceImpl extends ServiceImpl<SpecGroupMapper, SpecGroup> implements ISpecGroupService {


    @Autowired
    private ISpecService specService;

    @Override
    @Transactional(rollbackFor = SQLException.class)
    public boolean saveSpecGroup(String goodId, List<SpecGroup> specGroups) {
        if (CollectionUtils.isEmpty(specGroups)) {
            return true;
        }
        specGroups.forEach(e->e.setGoodId(goodId));
        baseMapper.delete(new LambdaQueryWrapper<SpecGroup>().eq(SpecGroup::getGoodId,goodId));
        if (saveBatch(specGroups)) {
            specGroups.forEach(e-> {
                specService.saveSpecList(e.getId(),e.getSpecList());
            });
            return true;
        }
        return false;
    }


    @Override
    public boolean delSpecGroup(String goodId) {
        List<SpecGroup> specGroups = list(new LambdaQueryWrapper<SpecGroup>().eq(SpecGroup::getGoodId,goodId));
        if (!CollectionUtils.isEmpty(specGroups)) {
            List<String> ids  = specGroups.stream().map(SpecGroup::getId).collect(Collectors.toList());
            if (specService.delBySpecGroupIds(ids)) {
                return removeByIds(ids);
            }
        }
        return false;
    }
}
