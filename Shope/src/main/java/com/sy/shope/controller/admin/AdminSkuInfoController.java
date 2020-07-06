package com.sy.shope.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sy.shope.entity.SkuInfo;
import com.sy.shope.service.facade.ISkuService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO
 * AdminSkuInfoController
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/admin/sku")
public class AdminSkuInfoController {

    @Autowired
    private ISkuService skuService;

    @GetMapping("/{page}")
    public ResponseEntity<JsonResult<IPage<SkuInfo>>> getByPage (@PathVariable  int page) {
        return ResponseEntity.ok(JsonResult.success(skuService.queryByPage(page,10,null)));
    }

    @GetMapping("/{page}/{goodId}")
    public ResponseEntity<JsonResult<IPage<SkuInfo>>> getByPage (@PathVariable  int page,
                                                                 @PathVariable String goodId) {
        return ResponseEntity.ok(JsonResult.success(skuService.queryByPage(page,10,goodId)));
    }

    @PutMapping
    public ResponseEntity<JsonResult<Boolean>> updateSku(@RequestBody SkuInfo skuInfo) {
        return ResponseEntity.ok(JsonResult.success(skuService.updateById(skuInfo)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResult<Boolean>> updateSku(@PathVariable String id) {
        return ResponseEntity.ok(JsonResult.success(skuService.removeById(id)));
    }
}
