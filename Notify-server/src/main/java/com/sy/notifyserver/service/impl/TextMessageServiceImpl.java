package com.sy.notifyserver.service.impl;



import com.sy.notifyserver.service.WeChatMessageService;
import com.sy.notifyserver.util.WeChatMessageFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: wang xiao
 * @description:
 * @date: Created in 14:29 2020/6/18
 */
@Service
public class TextMessageServiceImpl implements WeChatMessageService {

    @Override
    public String processMessage(Map<String, String> param) {
        String content = param.get("Content");
        String msgId = param.get("MsgId");

        return unSupportedMessage(param);
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        WeChatMessageFactory.addService("text",this);
    }
}
