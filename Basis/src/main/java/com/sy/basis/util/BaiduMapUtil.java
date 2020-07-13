package com.sy.basis.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 百度地图api
 * @author wangxiao
 * @since 1.1
 */
public class BaiduMapUtil {


    private final static String LANDMARK_URL = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=xQjYqVgtVeLa8sltPZvMkiIglpxWjS7p&output=json&coordtype=wgs84ll&location=LATI,LONG";

    private final static HttpClient CLIENT = HttpClientBuilder.create().build();
    

    public static JsonNode  getLandmark (String latitude,String longitude) {
        String url = LANDMARK_URL.replace("LATI",latitude).replace("LONG",longitude);
        JsonNode result = null;
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = CLIENT.execute(get);
            HttpEntity resp = response.getEntity();
            String content = EntityUtils.toString(resp);
            JsonNode  respResult = JsonUtil.parseJsonToJsonNode(content);
            if (Constants.ERROR_CODE.equals(respResult.getString(Constants.STATUS).asText())) {
                result = respResult.get("result");
            }
        }catch (Exception e) {
            return result;
        }
        return result;
    }

    public static JsonNode  getLandmark (String location) {
        JsonNode result = null;
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak=xQjYqVgtVeLa8sltPZvMkiIglpxWjS7p&output=json&coordtype=wgs84ll&location="+location;
        try {
            HttpGet get = new HttpGet(url);
            HttpResponse response = CLIENT.execute(get);
            HttpEntity resp = response.getEntity();
            String content = EntityUtils.toString(resp);
            JsonNode  respResult = JsonUtil.parseJsonToJsonNode(content);
            if (Constants.ERROR_CODE.equals(respResult.getString(Constants.STATUS).asText())) {
                result = respResult.get("result");
            }
        }catch (Exception e) {
            return result;
        }
        return result;
    }


}
