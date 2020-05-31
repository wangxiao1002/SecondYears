package com.sy.notifyserver.controller;

import com.sy.notifyserver.support.JsonResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 直播
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/room")
public class WebSocketController {

    @GetMapping("/{page}")
    public ResponseEntity<JsonResult> querySocketRoomByPage (@PathVariable int page) {
        return null;
    }


    @GetMapping("/{type}/{page}")
    public ResponseEntity<JsonResult> querySocketRoomByPageAndType (@PathVariable String type,
                                                                    @PathVariable int  page) {
        return null;
    }

}
