package com.sy.shope.service.facade;



import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.Order;
import com.sy.shope.entity.OrderDTO;

import java.util.List;

/**
 * @author: wang xiao
 * @description: orderService
 * @date: Created in 20:46 2020/6/3
 */
public interface IOrderService extends IService<Order> {

    /**
     *  添加订单并且返回订单尸体被
     * @author wangxiao
     * @date 20:48 2020/6/3
     * @param orderDTOS 订单创建信息
     * @param userId  用户
     * @return Order
     */
     Order addOrders(List<OrderDTO> orderDTOS, String userId);

     /**
      *  取消订单
      * @author wangxiao
      * @date 14:52 2020/6/4
      * @param orderId
      * @return boolean
      */
     boolean cancelOrder(String orderId);


     /**
      *  完成订单
      * @author wangxiao
      * @date 14:52 2020/6/4
      * @param orderId
      * @return boolean
      */
     boolean successOrder(String orderId);


    /**
     *  退款
     * @param orderId
     * @return
     */
    boolean refundOrder(String orderId);

     /**
      *  查询订单
      * @author wangxiao
      * @date 15:22 2020/6/4
      * @param orderId 订单id
      * @return Order
      */
     Order queryOrderByOderId(String orderId);


}
