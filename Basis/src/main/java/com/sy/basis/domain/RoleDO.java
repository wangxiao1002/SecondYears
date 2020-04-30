package com.sy.basis.domain;

import com.sy.basis.common.BaseDO;

/**
 * 用户角色
 * @author wangxiao
 * @since 1.1
 */
public class RoleDO  extends BaseDO {

    private String label;

    private String code;

    private Integer priority;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "RoleDO{" +
                "label='" + label + '\'' +
                ", code='" + code + '\'' +
                ", priority=" + priority +
                '}';
    }
}
