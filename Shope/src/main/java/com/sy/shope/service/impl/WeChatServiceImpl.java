package com.sy.shope.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.sy.shope.config.WeChatConfig;
import com.sy.shope.service.facade.IOrderService;
import com.sy.shope.service.facade.WeChatService;
import com.sy.shope.support.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Slf4j
@Service
public class WeChatServiceImpl implements WeChatService {


    @Autowired
    private WeChatConfig wxPayConfig;

    @Override
    public JsonResult unifiedOrder(String orderNo, BigDecimal amount, String body) {

        Map<String, String> returnMap = new HashMap<>(8);
        Map<String, String> responseMap = new HashMap<>(8);
        Map<String, String> requestMap = new HashMap<>();
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
            final String success = "SUCCESS";
            if (success.equals(returnCode)) {
                String resultCode = resultMap.get("result_code");
                if (success.equals(resultCode)) {
                    responseMap = resultMap;
                }
            }
            if (responseMap.isEmpty()) {
                return JsonResult.fail("获取预支付交易会话标识失败");
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
        return null;
    }

    @Override
    public JsonResult<String> refund(String orderNo, BigDecimal amount, String refundReason) throws Exception {
        return null;
    }
}
