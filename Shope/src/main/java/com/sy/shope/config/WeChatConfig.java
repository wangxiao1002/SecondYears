package com.sy.shope.config;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.io.InputStream;

/**
 * 微信配置信息
 * @author wangxiao
 * @since 1.1
 */
@Component
@ConfigurationProperties(prefix = "pay.we-chat")
public class WeChatConfig extends WXPayConfig {

    /**
     * appID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * API 密钥
     */
    private String key;

    /**
     * API证书绝对路径 (本项目放在了 resources/cert/wxpay/apiclient_cert.p12")
     */
    private String certPath;

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    private int httpConnectTimeoutMs = 8000;

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    private int httpReadTimeoutMs = 10000;

    /**
     * 微信支付异步通知地址
     */
    private String payNotifyUrl;



    /**
     * 微信退款异步通知地址
     */
    private String refundNotifyUrl;

    public WeChatConfig() {
        super();
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public String getMchId() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(certPath);
        return inputStream;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo("", true);
            }
        };
        return iwxPayDomain;
    }

    public String getPayNotifyUrl() {
        return payNotifyUrl;
    }

    public String getRefundNotifyUrl() {
        return refundNotifyUrl;
    }


    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
        this.httpConnectTimeoutMs = httpConnectTimeoutMs;
    }

    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        this.httpReadTimeoutMs = httpReadTimeoutMs;
    }

    public void setPayNotifyUrl(String payNotifyUrl) {
        this.payNotifyUrl = payNotifyUrl;
    }

    public void setRefundNotifyUrl(String refundNotifyUrl) {
        this.refundNotifyUrl = refundNotifyUrl;
    }
}
