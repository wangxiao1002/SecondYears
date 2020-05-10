package com.sy.auth.facde.service;

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
     * @return 1.1
     */
    int saveLogs(List<LogEntity> logEntities);
}
