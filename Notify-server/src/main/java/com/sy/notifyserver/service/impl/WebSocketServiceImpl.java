package com.sy.notifyserver.service.impl;

import com.sy.notifyserver.domain.WebSocketRoom;
import com.sy.notifyserver.mapper.WebSocketRoomMapper;
import com.sy.notifyserver.service.WebSocketService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 直播间
 * @author wangxiao
 * @since 1.1
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Resource
    private WebSocketRoomMapper socketRoomMapper;
    @Override
    public List<WebSocketRoom> querySocketRoomByCoordinate(double minX, double maxX, double minY, double maxY) {
        return socketRoomMapper.selectSocketRoomByCoordinate(minX, maxX, minY, maxY);
    }
}
