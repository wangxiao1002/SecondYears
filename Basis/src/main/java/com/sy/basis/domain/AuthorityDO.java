package com.sy.basis.domain;

import com.sy.basis.common.BaseDO;

/**
 * 权限点
 * @author wangxiao
 * @since
 */
public class AuthorityDO  extends BaseDO {

    private String label;

    /**
     * uri 是方法加地址的组合  中间是用@ 符号 方法大写
     */
    private String uri;

    private String code;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AuthorityDO{" +
                "label='" + label + '\'' +
                ", uri='" + uri + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
