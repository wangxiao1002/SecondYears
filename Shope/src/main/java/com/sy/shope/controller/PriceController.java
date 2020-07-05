package com.sy.shope.controller;


import com.sy.shope.service.facade.IPriceService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang xiao
 *  价格 计算
 * @date Created in 18:49 2020/6/3
 */
@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private IPriceService priceService;

    @GetMapping("/{spuId}")
    public ResponseEntity<JsonResult<String>> calcPrice (@PathVariable String spuId,
                                                         @RequestParam String ids) {
        return ResponseEntity.ok(JsonResult.success(priceService.getPrice(spuId, ids)));
    }
}
