package com.sy.notifyserver.service;

import com.sy.notifyserver.message.Message;

import java.util.Map;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
public interface MessageService {

    /**
     * 推送消息
     * @param message
     * @return
     */
    Map<String,Object> pushMessage(Message message);
}
