package com.sy.shope.service.facade;

import com.fasterxml.jackson.databind.JsonNode;
import com.sy.shope.entity.User;
import com.sy.shope.support.JsonResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * weixin 接口
 * @author wangxiao
 * @since 1.1
 */
public interface WeChatService {

    /**
     *  微信支付统一下单
     * @author wangxiao
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param body: 订单描述
     * @author wangxiao
     * @return JsonResult
     */
    JsonResult<Map<String,String>>  unifiedOrder(String orderNo, BigDecimal amount, String body) ;

    /**
     * 订单支付异步通知
     * @author  wangxiao
     * @param notifyStr: 微信异步通知消息字符串
     * @throws Exception
     * @return String
     */
    String notify(String notifyStr) throws Exception;

    /**
     * 退款
     * @author wangxiao
     * @param orderNo: 订单编号
     * @param amount: 实际支付金额
     * @param refundReason: 退款原因
     * @throws Exception
     * @return String
     */
    JsonResult<String>  refund(String orderNo, BigDecimal amount, String refundReason) throws Exception;

     /**
      * 获取微信登录验证码
      * @author wangxiao
      * @return
      * @throws UnsupportedEncodingException
      **/
    String weChatAuth() throws UnsupportedEncodingException;

    /**
     * 获取微信token和OpenID
     * @param code
     * @return
     */
    JsonNode getAccessToken(String code);

    /**
     * 获取微信tokenString
     * @param code
     * @return
     */
    String accessToken(String code);

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    JsonNode getUserInfo(String accessToken,String openId);

    /**
     * 微信授权回调
     * @param code
     * @return
     */
    User wxAuth(String code);
}
