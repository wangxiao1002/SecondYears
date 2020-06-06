package com.sy.shope.service.facade;



import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.OrderInfo;

import java.util.List;

/**
 * @author: wang xiao
 * @description: orderInfo service
 * @date: Created in 14:07 2020/6/4
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    
    /**
     *  通过订单id查询订单信息
     * @author wangxiao
     * @date 15:26 2020/6/4 
     * @param orderId
     * @return java.util.List
     */
    List<OrderInfo> queryOderInfoByOrderId(String orderId);
}
