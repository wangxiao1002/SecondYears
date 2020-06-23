package com.sy.notifyserver.controller;

import com.sy.notifyserver.message.Message;
import com.sy.notifyserver.service.MessageService;
import com.sy.notifyserver.service.WebSocketService;
import com.sy.notifyserver.support.JsonResult;
import com.sy.notifyserver.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 消息处理
 * @author wangxiao
 * @since 1.1
 */

@RestController
@RequestMapping("/message")
public class MessageController {




    @Autowired
    private MessageService messageService;


    @PostMapping("/push")
    public ResponseEntity<JsonResult> pushMessage (@RequestBody Message message) {
        Map<String,Object> result = messageService.pushMessage(message);
        return ResponseEntity.ok(JsonResult.build(200).value(result));
    }

}
