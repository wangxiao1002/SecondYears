package com.sy.shope.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品管理
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/admin/good")
public class AdminGoodController {

    @Autowired
    private IGoodService goodService;

    @GetMapping("/{page}")
    public ResponseEntity<JsonResult<IPage<Good>>> getPageGood (@PathVariable long page) {
        IPage<Good> goodPage = goodService.page(new Page<Good>(page,10L));
        return ResponseEntity.ok(JsonResult.success(goodPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResult<Good>> getGood (@PathVariable String id) {
        Good good = goodService.queryGoodDetails(id);
        return ResponseEntity.ok(JsonResult.success(good));
    }

    @PostMapping
    public ResponseEntity<JsonResult<Boolean>> addGood (@RequestBody @Valid Good param) {
        boolean result = goodService.save(param);
        return ResponseEntity.ok(JsonResult.success(result));
    }

    @PutMapping
    public ResponseEntity<JsonResult<Boolean>> updateGood (@RequestBody @Valid Good param) {
        boolean result = goodService.updateById(param);
        return ResponseEntity.ok(JsonResult.success(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResult<Boolean>> delGood (@PathVariable String id) {
        boolean result = goodService.removeById(id);
        return ResponseEntity.ok(JsonResult.success(result));
    }
}
