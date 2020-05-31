package com.sy.notifyserver.controller;

import com.spatial4j.core.shape.Rectangle;
import com.sy.notifyserver.config.WebSocketConfig;
import com.sy.notifyserver.domain.WebSocketRoom;
import com.sy.notifyserver.service.WebSocketService;
import com.sy.notifyserver.support.JsonResult;
import com.sy.notifyserver.util.RectangleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sy.notifyserver.util.RectangleUtil.getRectangle;

/**
 * 直播
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/room")
public class WebSocketController {

    @Autowired
    private WebSocketService socketService;

    @GetMapping("/{page}")
    public ResponseEntity<JsonResult> querySocketRoomByPage (@PathVariable int page) {
        return null;
    }


    @GetMapping("/{type}/{page}")
    public ResponseEntity<JsonResult> querySocketRoomByPageAndType (@PathVariable String type,
                                                                    @PathVariable int  page) {
        return null;
    }

    @GetMapping("nearby")
    public ResponseEntity<JsonResult> queryNearbySocketRoom(@RequestParam("distance") double distance,
                                                            @RequestParam("userLng") double userLng,
                                                            @RequestParam("userLat") double userLat) {

        Rectangle rectangle = RectangleUtil.getRectangle(distance, userLng, userLat);

        List<WebSocketRoom> roomList = socketService.querySocketRoomByCoordinate(rectangle.getMinX(), rectangle.getMaxX(), rectangle.getMinY(), rectangle.getMaxY());
        roomList = roomList.stream()
                .filter(a -> RectangleUtil.getDistance(a.getRoomLng(), a.getRoomLat(), userLng, userLat) <= distance)
                .collect(Collectors.toList());
        return ResponseEntity.ok(JsonResult.build(200).value(roomList));
    }




}
