package com.sy.basis.domain;

/**
 * 用户账号密码登录实体类
 * @author wangxiao
 * @since
 */
public class LoginDO {

    private String label;

    private String code;

    private String password;

    private String verifyCode;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "LoginDO{" +
                "label='" + label + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
