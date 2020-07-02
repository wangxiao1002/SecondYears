package com.sy.shope.controller;

import com.sy.shope.entity.PrizeDomain;
import com.sy.shope.service.facade.IndexService;
import com.sy.shope.support.JsonResult;
import com.sy.shope.tools.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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



    @GetMapping("/page")
    public ResponseEntity<JsonResult<Map<String,Object>>> indexPage (@RequestHeader String token) {
        String userId = jwtUtil.getTokenUserId(token);
        log.info("user: {} has query index page",userId);
        return ResponseEntity.ok(indexService.queryIndexPage(userId));
    }

    @GetMapping("/lottery")
    public ResponseEntity<JsonResult<PrizeDomain>> lottery (@RequestHeader String token,
                                                            @RequestParam String lotteryId) {
        String userId = jwtUtil.getTokenUserId(token);
        log.info("user id : {} has lottery",userId);
        PrizeDomain result = indexService.lottery(userId,lotteryId);
        log.warn("user : {} lottery activity :{} result is {}",userId,lotteryId,result.toString());
        return ResponseEntity.ok(JsonResult.success(result));
    }


}
