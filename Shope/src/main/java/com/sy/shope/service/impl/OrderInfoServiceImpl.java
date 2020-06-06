package com.sy.shope.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.OrderInfo;
import com.sy.shope.mappers.OrderInfoMapper;
import com.sy.shope.service.facade.IOrderInfoService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author: wang xiao
 * @description: orderInfoServiceImpl
 * @date: Created in 14:08 2020/6/4
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper,OrderInfo> implements IOrderInfoService {


    @Override
    public List<OrderInfo> queryOderInfoByOrderId(String orderId) {
        return baseMapper.selectOderInfoByOrderId(orderId);
    }
}
