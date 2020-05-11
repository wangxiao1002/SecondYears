package com.sy.basis.domain;

import com.sy.basis.common.MailType;

import java.util.Map;

/**
 * 邮件实体类
 * @author wangxiao
 * @since 1.1
 */
public class MailEntity {

    /**
     * 发送者
     */
    private String from;
    /**
     * 接收者
     */
    private String to;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String content;
    /**
     * 类型
     */
    private MailType type;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 模板参数
     */
    private Map<String,Object> variables;
    /**
     * 附件地址
     */
    private String attachPath;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getAttachPath() {
        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    private MailEntity  () {}

    public static MailEntity build () {
        return new MailEntity();
    }


   public MailEntity from (String from) {
        this.from = from;
        return this;
   }

    public MailEntity to (String to) {
        this.to = to;
        return this;
    }

    public MailEntity subject (String subject) {
        this.subject = subject;
        return this;
    }

    public MailEntity content (String content) {
        this.content = content;
        return this;
    }

    public MailEntity type (MailType type) {
        this.type = type;
        return this;
    }

    public MailEntity templateName (String templateName) {
        this.templateName = templateName;
        return this;
    }

    public MailEntity variables (Map<String,Object> variables) {
        this.variables = variables;
        return this;
    }

    public MailEntity attachPath (String attachPath) {
        this.attachPath = attachPath;
        return this;
    }




}
