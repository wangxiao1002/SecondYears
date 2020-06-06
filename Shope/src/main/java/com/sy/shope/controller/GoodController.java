package com.sy.shope.controller;


import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{page}")
    public ResponseEntity<JsonResult<Good>> getGoodsOfPage(@PathVariable int page) {
        return ResponseEntity.ok(JsonResult.success(goodService.getById(page)));
    }

//    @GetMapping("/details/{spuId}")
//    public ResponseEntity<JsonResult<Good>> getGoodDetails(@PathVariable String spuId) {
//        Assert.notNull(spuId,"spu id is must`t null ");
//        return ResponseEntity.ok(JsonResult.success(goodService.queryGoodDetails(spuId)));
//    }

}
