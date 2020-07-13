package com.sy.basis.util;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.time.Duration;

/**
 * 百度地图api
 * @author wangxiao
 * @since 1.1
 */
public class BaiduMapUtil {


    private final static OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(Duration.ofSeconds(10))
            .writeTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(5))
            .callTimeout(Duration.ofSeconds(15))
            .build();
    

    public static JsonNode  getLandmark (String latitude,String longitude) {
        String location = String.format("%s,%s",latitude,longitude);
        return getLandmark(location);
    }

    public static JsonNode getLandmark (String location) {
        JsonNode result = null;
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=xQjYqVgtVeLa8sltPZvMkiIglpxWjS7p&output=json&coordtype=wgs84ll&location="+location;
        return callBaiMap(url);
    }

    private static JsonNode callBaiMap (String url) {
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).get().build();
        final Call call = CLIENT.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                String respBody = response.body().string();
                JsonNode jsonNode = JsonUtil.parseJsonToJsonNode(respBody);
                return jsonNode.get("result");
            }
            return null;
        }catch (Exception e) {
            return null;
        }
    }




}
