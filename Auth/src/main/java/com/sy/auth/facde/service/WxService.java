package com.sy.auth.facde.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.sy.basis.domain.LoginDO;

import java.io.UnsupportedEncodingException;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
public interface WxService {


    /**
     * 获取二维码地址
     * @return
     * @throws UnsupportedEncodingException
     */
    String getWeChatAuthUrl() throws UnsupportedEncodingException;

    /**
     * 获取token
     * @param code
     * @return
     */
    JsonNode getAccessToken(String code);

    /**
     * 获取微信token字符串
     * @param code
     * @return
     */
    String accessToken(String code);

    /**
     * 获取微信用户信息
     * @param accessToken
     * @param openId
     * @return
     */
    JsonNode getUserInfo(String accessToken, String openId);

    /**
     * 微信认证
     * @param code
     * @return
     */
    LoginDO wxAuth(String code);
}
