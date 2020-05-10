package com.sy.auth.facde.service;

import com.github.pagehelper.PageInfo;
import com.sy.auth.support.LogDTO;
import com.sy.basis.log.LogEntity;

import java.util.List;

/**
 * 日志提供类
 * @author wangxiao
 * @since 1.1
 */
public interface LogService {


    /**
     * 保存日志
     * @param logEntities 日志集合
     * @return int  受影响的行数
     */
    int saveLogs(List<LogEntity> logEntities);


    /**
     * 查询日志
     * @param logDTO 查询条件
     * @return
     */
    PageInfo<LogEntity> queryLog (LogDTO logDTO);
}
