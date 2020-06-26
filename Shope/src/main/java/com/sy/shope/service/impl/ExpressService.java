package com.sy.shope.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sy.shope.tools.ExpressUtil;
import com.sy.shope.tools.JsonUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 快递查询
 * @author wangxiao
 * @since 1.1
 */
@Service
public class ExpressService {

    Cache<String,String> TOKEN_CACHE = CacheBuilder.newBuilder()
            .maximumSize(20)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .concurrencyLevel(10)
            .recordStats()
            .build();
    final String TOKEN_KEY = "express_token";

    public JsonNode getExpressByNu (String number) {
        String token = TOKEN_CACHE.getIfPresent(TOKEN_KEY);
        if (Objects.isNull(token)) {
            token = ExpressUtil.getBaiduExpressToken();
            TOKEN_CACHE.put(TOKEN_KEY,token);
        }
        String result = ExpressUtil.searchExpressByNu(token,number);
        return JsonUtil.parseJsonToJsonNode(result);
    }
}
