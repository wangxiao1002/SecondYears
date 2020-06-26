package com.sy.shope.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.service.impl.ElasticService;
import com.sy.shope.support.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    private ElasticService elasticService;



    @GetMapping("/{page}")
    public ResponseEntity<JsonResult<Page<Good>>> getGoodsOfPage(@PathVariable int page) {
        return ResponseEntity.ok(JsonResult.success(goodService.queryOrderPage(page)));
    }

    @GetMapping("/details/{spuId}")
    public ResponseEntity<JsonResult<Good>> getGoodDetails(@PathVariable String spuId) {
        Assert.notNull(spuId,"spu id is must`t null ");
        return ResponseEntity.ok(JsonResult.success(goodService.queryGoodDetails(spuId)));
    }

    @GetMapping("/search")
    public ResponseEntity<JsonResult<List<Good>>> searchGood (@RequestParam String keyWord,
                                                              @RequestParam String type,
                                                              @RequestParam int page) {
        return ResponseEntity.ok(JsonResult.success(elasticService.queryAndHeight(keyWord,type,page,10)));
    }

}
