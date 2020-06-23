package com.sy.basis.log;

/**
 * 成功 日志处理
 * @author wangxiao
 * @since
 */

public class SuccessLogHandler extends LogHandler {
    @Override
    public void handler(LogEntity logEntity) {
        if ("success".equals(logEntity.getStatus())) {
            // 日志处理
        }else {
            next.handler(logEntity);
        }
    }
}
