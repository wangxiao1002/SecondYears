package com.sy.basis.domain;

import com.sy.basis.common.BaseDO;

import java.util.List;

/**
 * 菜单
 * @author wangxiao
 * @since 1.1
 */
public class MenuDTO extends BaseDO {

    private String label;

    private String code;

    private Integer order;

    private String url;



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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "label='" + label + '\'' +
                ", code='" + code + '\'' +
                ", order=" + order +
                ", url='" + url + '\'' +
                '}';
    }
}
