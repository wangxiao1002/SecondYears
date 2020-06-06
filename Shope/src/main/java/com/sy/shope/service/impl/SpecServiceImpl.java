package com.sy.shope.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroupVO;
import com.sy.shope.mappers.SpecMapper;
import com.sy.shope.service.facade.ISpecService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wang xiao
 * @description: spec serviceImpl
 * @date: Created in 17:13 2020/6/3
 */
@Service
public class SpecServiceImpl extends ServiceImpl<SpecMapper, Spec> implements ISpecService {



    @Override
    public List<SpecGroupVO> querySpecGroupByGoodId(String goodId) {
        return baseMapper.selectSpecGroupByGoodId(goodId);
    }
}
