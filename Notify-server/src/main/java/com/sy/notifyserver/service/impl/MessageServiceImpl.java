package com.sy.notifyserver.service.impl;

import com.sy.notifyserver.message.Message;
import com.sy.notifyserver.message.MessageType;
import com.sy.notifyserver.service.MessageService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * MessageServiceImpl
 * @author wangxiao
 * @since
 */
@Service
public class MessageServiceImpl implements MessageService, InitializingBean {

    private Map<MessageType, Function<Message,Map<String,Object>>> functionMap = new HashMap<>(8);



    @Override
    public Map<String, Object> pushMessage(Message message) {
        return functionMap.get(message.getType()).apply(message);
    }

    private Map<String,Object> doMessage(Message message) {

        // 发送一种消息类型
        return null;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        functionMap.put(MessageType.ANNOUNCEMENT,this::doMessage);
    }
}
