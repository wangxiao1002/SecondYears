package com.sy.basis.log;

/**
 * 记录日志实体
 * @author wangxiao
 * @since  1.1
 */
public class LogEntity {

    /**
     * 用户
     */
    private String user;

    /**
     * 请求地址
     */
    private String uri;

    /**
     * 方法
     */
    private String method;

    /**
     * 操作描述
     */
    private String desc;

    /**
     * url 请求参数
     */
    private String queryParams;
    /**
     * body 请求参数
     */
    private String bodyParams;
    /**
     * 返回结果
     */
    private String result;

    public LogEntity() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    public String getBodyParams() {
        return bodyParams;
    }

    public void setBodyParams(String bodyParams) {
        this.bodyParams = bodyParams;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LogEntity user(String user) {
        this.user = user;
        return this;
    }
    public LogEntity uri(String uri) {
        this.uri = uri;
        return this;
    }
    public LogEntity method(String method) {
        this.method = user;
        return this;
    }
    public LogEntity desc(String desc) {
        this.desc = desc;
        return this;
    }
    public LogEntity queryParams(String queryParams) {
        this.queryParams = queryParams;
        return this;
    }
    public LogEntity bodyParams(String bodyParams) {
        this.bodyParams = bodyParams;
        return this;
    }
    public LogEntity result(String result) {
        this.result = result;
        return this;
    }
}
