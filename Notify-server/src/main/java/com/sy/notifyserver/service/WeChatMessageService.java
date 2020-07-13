package com.sy.notifyserver.service;

import com.alibaba.fastjson.JSONObject;
import com.zd.common.tools.WXMessage.WXMessageTools;
import com.zd.common.tools.WXMessage.WXTextMessage;
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
        WXTextMessage wxTextMessage  = new WXTextMessage();
        wxTextMessage.setContent(param.getOrDefault("Content","欢迎关注中德智慧教育,该二维码已经被激活，请您选择官方渠道商品，期待与你下次相遇"));
        wxTextMessage.setCreateTime(System.currentTimeMillis());
        wxTextMessage.setFuncFlag(0);
        wxTextMessage.setMsgType("text");
        wxTextMessage.setFromUserName(param.get("ToUserName"));
        wxTextMessage.setToUserName(param.get("FromUserName"));
        return WXMessageTools.textMessageXml(wxTextMessage);
    }

    /**
     *  初始化消息模板data
     * @author wangxiao
     * @date 17:13 2020/7/9
     * @param data
     * @return java.lang.String
     */
    default JSONObject initData (Map<String,Object> data) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String,Object> entry :data.entrySet()) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("value",entry.getValue());
            tempObject.put("color","#173177");
            jsonObject.put(entry.getKey(),tempObject);
        }
        return jsonObject;
    }
}
