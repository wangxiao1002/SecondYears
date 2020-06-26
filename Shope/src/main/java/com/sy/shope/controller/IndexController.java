package com.sy.shope.controller;

import com.sy.shope.service.facade.IndexService;
import com.sy.shope.support.JsonResult;
import com.sy.shope.tools.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 商城首页
 * @author wangxiao
 * @since 1.1
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {


    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private IndexService indexService;

    @GetMapping
    public ResponseEntity<JsonResult> indexPage (@RequestHeader String token) {
        String userId = jwtUtil.getTokenUserId(token);
        log.info("user: {} is query index page",userId);
        return ResponseEntity.ok(indexService.queryIndexPage(userId));
    }


}
