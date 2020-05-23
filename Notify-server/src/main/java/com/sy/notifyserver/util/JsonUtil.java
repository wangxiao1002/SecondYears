package com.sy.notifyserver.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



import java.text.SimpleDateFormat;
import java.util.Objects;


/**
 * json until
 * @author wangxiao
 * @since 1.1
 */
public class JsonUtil {

    private JsonUtil () {}

    private static ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 反序列化多出来熟悉 不抛出异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 空 不抛出异常
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static <T> String convertString (T t)  {
        try {
            return Objects.nonNull(t)? MAPPER.writeValueAsString(t):null;
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T convertBean (String jsonString,Class<T> clazz)  {
        if (null == jsonString || "".equals(jsonString.trim()) || null == clazz){
            return null;
        }
        try {
            return MAPPER.readValue(jsonString,clazz);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }
}
