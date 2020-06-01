package com.sy.notifyserver.mapper;

import com.sy.notifyserver.domain.WebSocketRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 直播间 mapper
 * @author wangxiao
 * @since 1.1
 */
@Mapper
public interface WebSocketRoomMapper {

    /**
     * 查询固定内直播间
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @return
     */
    @Select("SELECT r.`code`,r.title,r.user_id,r.type_id,r.address " +
            "FROM  t_websocket_room r WHERE  r.room_lng BETWEEN  #{minX} AND #{maxX} " +
            "AND r.room_lat BETWEEN  #{minY} AND #{maxY} ")
    @Results(value = {
            @Result(column = "code",property = "code"),
            @Result(column = "title",property = "title"),
            @Result(column = "user_id",property = "userId"),
            @Result(column = "type_id",property = "typeId"),
            @Result(column = "address",property = "address"),
    })
    List<WebSocketRoom> selectSocketRoomByCoordinate(double minX, double maxX, double minY, double maxY);


}
