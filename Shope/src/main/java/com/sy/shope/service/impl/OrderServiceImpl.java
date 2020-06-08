package com.sy.shope.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Order;
import com.sy.shope.entity.OrderDTO;
import com.sy.shope.entity.OrderInfo;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.mappers.OrderMapper;
import com.sy.shope.service.facade.IOrderInfoService;
import com.sy.shope.service.facade.IOrderService;
import com.sy.shope.service.facade.IPriceService;
import com.sy.shope.service.facade.ISkuService;
import com.sy.shope.support.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author: wang xiao
 * @description: orderServiceImpl
 * @date: Created in 20:49 2020/6/3
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISkuService skuService;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IPriceService priceService;

    private OrderExpireHandler expireHandler;

    @Autowired
    public void setExpireHandler(OrderExpireHandler expireHandler) {
        this.expireHandler = expireHandler;
    }

    @Override
    public Order addOrders(List<OrderDTO> orderDTOS, String userId) {
        if (null == orderDTOS || orderDTOS.isEmpty()) {
            throw new OrderingException("200","请选择商品信息");
        }
        Order order = new Order();
        order.setCreateTime(LocalDateTime.now());
        order.setUserId(userId);
        List<OrderInfo> orderInfos = new ArrayList<>(orderDTOS.size());
        for (OrderDTO orderDTO : orderDTOS) {
            OrderInfo tempOrderInfo = new OrderInfo();
            tempOrderInfo.setCreateTime(LocalDateTime.now());
            tempOrderInfo.setCount(orderDTO.getCount());
            tempOrderInfo.setUserId(userId);
            tempOrderInfo.setSpuId(orderDTO.getSpuId());
            SkuInfo skuInfo =  skuService.querySkuBySpuIdAndIds(orderDTO.getSpuId(),orderDTO.getIds());
            tempOrderInfo.setSkuId(skuInfo.getId());
            tempOrderInfo.setCount(orderDTO.getCount());
            tempOrderInfo.setPrice(priceService.getSkuPrice(skuInfo));
            orderInfos.add(tempOrderInfo);
            priceService.getSkuPrice(skuInfo);
        }
        order.setState(OrderState.WAIT_PAY);
        // 计算价格
        OrderPriceDTO orderPriceDTO = priceService.calcOrderPrice(orderInfos);
        order.setPrice(orderPriceDTO.getPrice());
        order.setPayPrice(orderPriceDTO.getPayPrice());
        if (!save(order)) {
            throw new OrderingException("200","下单异常");
        }
        orderPriceDTO.getOrderInfos().forEach(e->e.setOrderId(order.getId()));
        for (OrderInfo orderInfo : orderPriceDTO.getOrderInfos()) {
            orderInfoService.save(orderInfo);
        }
        OrderExpireDTO<String> orderExpireDTO = new OrderExpireDTO<String>(20L, TimeUnit.SECONDS,order.getId());
        expireHandler.addOrderExpire(orderExpireDTO);
        return order;
    }


    @Override
    public boolean cancelOrder(String orderId) {
        Order order = getById(orderId);
        boolean a = Optional.ofNullable(order).map(e ->{
            e.setState(OrderState.CANCEL);
                    return updateById(order);})
                .orElse(false);
        return a;
    }

    @Override
    public boolean successOrder(String orderId) {
        Order order = getById(orderId);
        boolean a = Optional.ofNullable(order).map(e ->{
            e.setState(OrderState.DONE);
            return updateById(order);})
                .orElse(false);
        return a;
    }


    @Override
    public Order queryOrderByOderId(String orderId) {
        Order order = getById(orderId);
        Assert.notNull(order,"订单不存在");
        order.setOrderInfos(orderInfoService.queryOderInfoByOrderId(orderId));
        return order;
    }
}
