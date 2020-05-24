package com.sy.notifyserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息处理
 * @author wangxiao
 * @since 1.1
 */

@RestController
@RequestMapping("/message")
public class MessageController {

    @GetMapping("/push/{userId}")
    public void pushTest (@PathVariable String userId) {

    }
}
