package com.sy.auth.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * json util
 * @author wangxiao
 * @since 1.1
 */
public class JsonUtil {

    private JsonUtil() {}

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static String toJsonString (Object object)   {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        }catch (JsonProcessingException e) {
            return null;
        }
    }

    public static JsonNode toJsonNode (Object object) {
        return OBJECT_MAPPER.valueToTree(object);
    }

    public static <T> T parseJsonNodeToObject(JsonNode jsonNode, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.treeToValue(jsonNode,beanType);
        }catch (JsonProcessingException e) {
            return null;
        }

    }

    public  static <T> T parseJsonString (String jsonStr, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr,beanType);
        }catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> List<T> parseJsonToList(String jsonArrayStr, Class<T> beanType) {
        try {
            List<T> list = OBJECT_MAPPER.readValue(jsonArrayStr, new TypeReference<List<T>>() {});
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String,Object> parseJsonToMap(String jsonStr) {
       try {
           return OBJECT_MAPPER.readValue(jsonStr,new TypeReference<Map<String,Object>>(){});
       }catch (JsonProcessingException e) {
           return null;
       }

    }

    public JsonNode parseJsonToJsonNode (String jsonStr) {
        try {
            return OBJECT_MAPPER.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
