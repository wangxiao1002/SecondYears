package com.sy.shope.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        IPage<Good> goodIPage = goodService.page(new Page<Good>(page));
    }
}
