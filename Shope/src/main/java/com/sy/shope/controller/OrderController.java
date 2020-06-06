package com.sy.shope.controller;


import com.sy.shope.entity.Order;
import com.sy.shope.entity.OrderDTO;
import com.sy.shope.service.facade.IOrderService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: wang xiao
 * @description: orderController
 * @date: Created in 20:32 2020/6/3
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseEntity<JsonResult<Order>> addOrder(@Valid  @RequestBody List<OrderDTO> params) {
        return ResponseEntity.ok(JsonResult.success(orderService.addOrders(params,"123")));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<JsonResult<Order>> getOrder(@PathVariable String orderId) {
        return ResponseEntity.ok(JsonResult.success(orderService.queryOrderByOderId(orderId)));

    }
}
