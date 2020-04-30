package com.sy.basis.common;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础类
 * @author wangxiao
 * @since
 */
public class BaseDO implements Serializable {

    private transient LocalDateTime createDate;

    private transient LocalDateTime modifyDate;

    @Override
    public String toString() {
        return "BaseDO{" +
                "createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
