package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Good;
import com.sy.shope.mappers.GoodMapper;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.service.facade.ISpecService;
import com.sy.shope.support.OrderingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>  implements IGoodService {

    @Autowired
    private ISpecService specService;

    @Override
    public Page<Good> queryOrderPage(int current) {
        Page<Good> page = new Page<>(current,10);
        QueryWrapper<Good> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public Good queryGoodDetails(String goodId) {
        Good good = baseMapper.selectById(goodId);
        Optional.ofNullable(good).map(e->{
            e.setSpecGroups(specService.querySpecGroupByGoodId(e.getId()));
            return e;
        }).orElseThrow(() ->new OrderingException("200","查询商品不存在!!"));
        return good;
    }

    @Override
    public List<Good> queryUserLikeGood(String userId) {
        return null;
    }
}
