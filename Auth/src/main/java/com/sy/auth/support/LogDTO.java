package com.sy.auth.support;

import com.sy.basis.log.LogEntity;

/**
 * 日志查询参数
 * @author wangxiao
 * @since 1.1
 */
public class LogDTO extends LogEntity {

    private int pageNum;

    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "LogDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
