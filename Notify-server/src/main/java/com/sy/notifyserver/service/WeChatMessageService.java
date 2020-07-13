package com.sy.notifyserver.service;


import com.sy.basis.util.Constants;
import org.springframework.beans.factory.InitializingBean;


import java.util.Map;

/**
 * @author: wang xiao
 * @description: WeChatMessageService
 * @date: Created in 11:31 2020/6/18
 */
public interface WeChatMessageService extends InitializingBean {

    /**
     *  处理消息回调
     * @author wangxiao
     * @date 11:33 2020/6/18
     * @param param
     * @return java.lang.String
     */
    String processMessage(Map<String,String> param);


    /**
     *  默认不支持消息
     * @author wangxiao
     * @date 15:27 2020/7/1 
     * @param param
     * @return java.lang.String
     */
    default String unSupportedMessage(Map<String,String> param) {
        return Constants.FAIL;
    }

}
