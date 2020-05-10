package com.sy.auth.mapper;

import com.sy.auth.support.LogDTO;
import com.sy.basis.log.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日志
 * @author wangxiao
 * @since 1.1
 */
@Mapper
public interface LogMapper {

    /**
     *  保存日志
     * @param logEntities 日志集合
     * @return 受影响的行
     */
    int saveLogs (@Param("list") List<LogEntity> logEntities);


    /**
     *  查询日志
     * @param logDTO 查询参数 result 不参与条件
     * @return 日志集合
     */
    List<LogEntity> selectLogs (LogDTO logDTO);
}
