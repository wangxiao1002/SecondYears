package com.sy.notifyserver.service;

import com.sy.notifyserver.domain.WebSocketRoom;

import java.util.List;

/**
 * 直播间逻辑
 * @author wangxiao
 * @since 1.1
 */
public interface WebSocketService {

    /**
     * 求一个范围内的直播间
     * @param minX x min value
     * @param maxX x max value
     * @param minY y min value
     * @param maxY y max value
     * @return List of WebSocketRoom
     */
    List<WebSocketRoom> querySocketRoomByCoordinate (double minX,double maxX,double minY,double maxY);
}
