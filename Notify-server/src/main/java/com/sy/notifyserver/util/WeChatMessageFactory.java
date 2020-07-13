package com.sy.notifyserver.util;





import com.sy.notifyserver.service.WeChatMessageService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wang xiao
 * @description: 微信消息工厂
 * @date: Created in 11:43 2020/6/18
 */
public class WeChatMessageFactory {

    private final static Map<String, WeChatMessageService> SERVICE_MAP = new HashMap<>(16);


    public static WeChatMessageService getService (String key) {
        return SERVICE_MAP.get(key);
    }

    public static void addService (String key, WeChatMessageService messageService) {
        SERVICE_MAP.put(key, messageService);
    }


 }
