package com.sy.shope.controller;

import com.sy.shope.service.facade.WeChatService;
import com.sy.shope.support.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private WeChatService weChatService;

    @GetMapping
    public ResponseEntity<JsonResult> wxAuth (@RequestParam String code,
                                              @RequestParam String state) {
        return ResponseEntity.ok(JsonResult.success(weChatService.wxAuth(code)));
    }
}
