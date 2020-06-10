package com.sy.shope.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.support.JsonResult;

import com.sy.shope.support.OrderEvent;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author: wang xiao
 * @description: 商品
 * @date: Created in 12:00 2020/6/3
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private IGoodService goodService;

    @Autowired
    private ApplicationEventPublisher publisher;



    @GetMapping("/{page}")
    public ResponseEntity<JsonResult<Page<Good>>> getGoodsOfPage(@PathVariable int page) {
        return ResponseEntity.ok(JsonResult.success(goodService.queryOrderPage(page)));
    }

    @GetMapping("/details/{spuId}")
    public ResponseEntity<JsonResult<Good>> getGoodDetails(@PathVariable String spuId) {
        Assert.notNull(spuId,"spu id is must`t null ");
        return ResponseEntity.ok(JsonResult.success(goodService.queryGoodDetails(spuId)));
    }

}
