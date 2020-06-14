package com.sy.auth.facde.spring;

import com.fasterxml.jackson.databind.JsonNode;
import com.sy.auth.config.WeiXinConfig;
import com.sy.auth.facde.service.WxService;
import com.sy.auth.mapper.AuthMapper;
import com.sy.auth.utils.JsonUtil;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.util.SnowFlakeUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;

/**
 * weixin serivice
 * @author wangxiao
 * @since
 */

@Service
public class WxServiceImpl implements WxService {

    @Resource
    private AuthMapper authMapper;

    @Autowired
    private WeiXinConfig weiXinConfig;

    private Logger log = LoggerFactory.getLogger(WxServiceImpl.class);
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();



    @Override
    public String getWeChatAuthUrl() throws UnsupportedEncodingException {
        String url = weiXinConfig.getQrCodeUrl();
        String oauthUrl = url+"?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        String myRedirectUrl = URLEncoder.encode(weiXinConfig.getRedirectUrl(),"utf-8");
        oauthUrl =  oauthUrl.replace("APPID",weiXinConfig.getAppId())
                .replace("REDIRECT_URI",myRedirectUrl);
        log.info(oauthUrl);
        return oauthUrl;
    }

    @Override
    public JsonNode getAccessToken(String code)   {
        String url = weiXinConfig.getAccessTokenUrl();
        url = url.replace("APPID",weiXinConfig.getAppId())
                .replace("SECRET",weiXinConfig.getAppSecret())
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
        String url = weiXinConfig.getUserInfoUrl();
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
    public LoginDO wxAuth(String code) {
        JsonNode tokenJson = getAccessToken(code);
        if (Objects.isNull(tokenJson)) {
            throw new RuntimeException("微信授权失败");
        }
        String accessToken = tokenJson.get("access_token").asText();
        String openId = tokenJson.get("openid").asText();
        JsonNode userInfo = getUserInfo(accessToken,openId);
        LoginDO loginDO = authMapper.selectUserByOpenId(openId);
        if (Objects.isNull(loginDO)) {
            loginDO= new LoginDO();
            String userCode = String.valueOf(SnowFlakeUtil.nextId());
            loginDO.setLabel(userInfo.get("nickname").asText());
            loginDO.setCode(userCode);
            authMapper.insertLoginDto(loginDO);
            authMapper.insertUserOpenId(userCode,openId);
        }
        return loginDO;
    }
}
