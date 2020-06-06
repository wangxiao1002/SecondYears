package com.sy.shope.mappers;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.shope.entity.OrderInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author: wang xiao
 * @description:
 * @date: Created in 14:10 2020/6/4
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     *  通过订单id查询订单信息
     * @author wangxiao
     * @date 15:29 2020/6/4
     * @param orderId
     * @return java.util.List
     */
    @Select("")
    List<OrderInfo> selectOderInfoByOrderId(String orderId);
}
