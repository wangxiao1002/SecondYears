package com.sy.shope.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.shope.entity.Lottery;
import com.sy.shope.service.facade.ILotteryService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 * 抽奖管理
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/admin/lottery")
public class AdminLotteryController {


    @Autowired
    private ILotteryService lotteryService;

    @GetMapping
    public ResponseEntity<JsonResult<List<Lottery>>> getAllLotteries () {
        return ResponseEntity.ok(JsonResult.success(lotteryService.list()));
    }

    @GetMapping("/page")
    public ResponseEntity<JsonResult<IPage<Lottery>>> getAllLotteries (@RequestParam int page,
                                                                       @RequestParam int pageSize) {

        return ResponseEntity.ok(JsonResult.success(lotteryService.page(new Page<>(page,pageSize))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonResult<Lottery>> getLottery (@PathVariable String id) {
        return ResponseEntity.ok(JsonResult.success(lotteryService.getById(id)));
    }

    @PutMapping
    public ResponseEntity<JsonResult<Boolean>> updateLottery (@RequestBody Lottery lottery) {
        return ResponseEntity.ok(JsonResult.success(lotteryService.updateById(lottery)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JsonResult<Boolean>> deleteLottery (@PathVariable String id) {
        return ResponseEntity.ok(JsonResult.success(lotteryService.removeById(id)));
    }
}
