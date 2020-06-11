package com.sy.shope.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.sy.shope.config.WeChatConfig;
import com.sy.shope.entity.Order;
import com.sy.shope.entity.User;
import com.sy.shope.service.facade.IOrderService;
import com.sy.shope.service.facade.WeChatService;
import com.sy.shope.support.JsonResult;
import com.sy.shope.support.OrderEvent;
import com.sy.shope.support.OrderingException;
import com.sy.shope.tools.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {

    final String success = "SUCCESS";

    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();

    @Autowired
    private IOrderService orderService;


    @Autowired
    private WeChatConfig wxPayConfig;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public JsonResult<Map<String,String>> unifiedOrder(String orderNo, BigDecimal amount, String body) {

        Map<String, String> returnMap = new HashMap<>(8);
        Map<String, String> responseMap = new HashMap<>(8);
        Map<String, String> requestMap = new HashMap<>(8);
        try {
            WXPay wxpay = new WXPay(wxPayConfig);
            requestMap.put("body", body);
            requestMap.put("out_trade_no", orderNo);
            requestMap.put("total_fee", amount.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).setScale(0,4).toString());
            requestMap.put("trade_type", "Native");
            requestMap.put("notify_url", wxPayConfig.getPayNotifyUrl());
            Map<String, String> resultMap = wxpay.unifiedOrder(requestMap);
            for (String resultKey : resultMap.keySet()) {
                log.info("订单key:{}", resultMap.get(resultKey));
            }
            String returnCode = resultMap.get("return_code");

            if (success.equals(returnCode)) {
                String resultCode = resultMap.get("result_code");
                if (success.equals(resultCode)) {
                    responseMap = resultMap;
                }
            }
            if (responseMap.isEmpty()) {
                throw new OrderingException("502","获取预支付交易会话标识失败");
            }
            long time = System.currentTimeMillis() / 1000;
            String timestamp = String.valueOf(time);
            returnMap.put("appid", wxPayConfig.getAppId());
            returnMap.put("partnerid", wxPayConfig.getMchId());
            returnMap.put("prepayid", responseMap.get("prepay_id"));
            returnMap.put("noncestr", responseMap.get("nonce_str"));
            returnMap.put("timestamp", timestamp);
            returnMap.put("package", "Sign=WXPay");
            returnMap.put("sign", WXPayUtil.generateSignature(returnMap, wxPayConfig.getKey()));
            return JsonResult.success(returnMap);
        } catch (Exception e) {
            log.error("订单号：{}，错误信息：{}", orderNo, e.getMessage());
            return JsonResult.fail("微信支付统一下单失败");
        }
    }

    @Override
    public String notify(String notifyStr) throws Exception {
        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        try {
            Map<String, String> resultMap = WXPayUtil.xmlToMap(notifyStr);
            WXPay wxPay = new WXPay(wxPayConfig);
            if (wxPay.isPayResultNotifySignatureValid(resultMap)) {
                String returnCode = resultMap.get("return_code");
                String outTradeNo = resultMap.get("out_trade_no");
                String transactionId = resultMap.get("transaction_id");
                if (success.equals(returnCode)) {
                    if (! StringUtils.isEmpty(outTradeNo)) {
                        orderService.successOrder(outTradeNo);
                        publisher.publishEvent(new OrderEvent(outTradeNo));
                        log.info("微信手机支付回调成功,订单号:{}", outTradeNo);
                        xmlBack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlBack;
    }

    @Override
    public JsonResult<String> refund(String orderNo, BigDecimal amount, String refundReason) throws Exception {
        if(StringUtils.isEmpty(orderNo)){
            throw new OrderingException("501","订单编号不能为空");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 1){
            throw new OrderingException("501","金额必须大于0");
        }
        Order order = orderService.getById(orderNo);
        if (Objects.isNull(order)) {
            throw new OrderingException("501","订单不能为空");
        }
        Map<String, String> responseMap = new HashMap<>(8);
        Map<String, String> requestMap = new HashMap<>(8);
        WXPay wxpay = new WXPay(wxPayConfig);
        requestMap.put("out_trade_no", orderNo);
        requestMap.put("out_refund_no", orderNo);
        requestMap.put("total_fee", "订单总金额");
        requestMap.put("refund_fee", amount.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).setScale(0,4).toString());

        requestMap.put("refund_desc", refundReason);
        try {
            responseMap = wxpay.refund(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String responseKey : responseMap.keySet()) {
            log.info("订单key:{}", responseMap.get(responseKey));
        }
        String returnCode = responseMap.get("return_code");
        String returnMsg = responseMap.get("return_msg");
        if (success.equals(returnCode)) {
            String resultCode = responseMap.get("result_code");
            String errCodeDes = responseMap.get("err_code_des");
            if (success.equals(resultCode)) {
                orderService.refundOrder(orderNo);
                return JsonResult.success("success");
            } else {
                log.info("订单号:{}错误信息:{}", orderNo, errCodeDes);
                return JsonResult.fail(errCodeDes);
            }
        } else {
            log.info("订单号:{}错误信息:{}", orderNo, returnMsg);
            return JsonResult.fail(returnMsg);
        }

    }

    @Override
    public String weChatAuth() throws UnsupportedEncodingException {
        String url = wxPayConfig.getAuthUrl();
        String oauthUrl = url+"?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        String redirectUrl = URLEncoder.encode(wxPayConfig.getRedirectUrl(),"utf-8");
        oauthUrl =  oauthUrl.replace("APPID",wxPayConfig.getAppId())
                .replace("REDIRECT_URI",redirectUrl);
        log.info(oauthUrl);
        return oauthUrl;
    }

    @Override
    public JsonNode getAccessToken(String code)   {
        String url = wxPayConfig.getAccessToken();
        url = url.replace("APPID",wxPayConfig.getAppId())
                .replace("SECRET",wxPayConfig.getSecret())
                .replace("CODE",code);
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.setConfig(requestConfig);
        JsonNode result = null;
        try {
            HttpResponse response = httpClient.execute(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                result = JsonUtil.toJsonNode(entity);

            }
        }catch (IOException e) {
            e.printStackTrace();
            log.error("获取微信accessToken失败");
        }
        return result;
    }

    @Override
    public String accessToken(String code) {
        JsonNode jsonNode = getAccessToken(code);
        return Optional.ofNullable(jsonNode).map(e->e.get("access_token").asText()).orElse(null);
    }

    @Override
    public JsonNode getUserInfo(String accessToken, String openId) {
        String url = wxPayConfig.getUserInfo();
        url = url.replace("ACCESS_TOKEN",accessToken)
                .replace("OPENID",openId);
        HttpGet request = new HttpGet(url);
        HttpClient httpClient = HttpClients.createDefault();
        JsonNode result = null;
        try {
            request.setConfig(requestConfig);
            HttpResponse response = httpClient.execute(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                HttpEntity entity = response.getEntity();
                result = JsonUtil.toJsonNode(entity);

            }
        }catch (IOException e) {
            e.printStackTrace();
            log.error("获取微信accessToken失败");
        }
        return result;
    }

    @Override
    public User wxAuth(String code) {
        JsonNode tokenJson = getAccessToken(code);
        if (Objects.isNull(tokenJson)) {
            throw new OrderingException("500","微信授权失败");
        }
        String accessToken = tokenJson.get("access_token").asText();
        String openId = tokenJson.get("openid").asText();
        JsonNode userInfo = getUserInfo(accessToken,openId);
        /**
         * 查询用户是否存在 不存在新建 存在登录
         */
        return null;
    }
}
