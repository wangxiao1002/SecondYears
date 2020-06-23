package com.sy.basis.log;

/**
 * LogHandler
 * @author wangxiao
 * @since 1.1
 */
public abstract class LogHandler {

    protected LogHandler next;

    public void setNext(LogHandler next) {
        this.next = next;
    }

    /**
     * 处理数据
     */
    public abstract void handler(LogEntity logEntity);
}
