package com.sy.notifyserver.service.impl;



import com.sy.basis.util.Constants;
import com.sy.notifyserver.service.WeChatMessageService;
import com.sy.notifyserver.util.WeChatMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author: wang xiao
 * @description: 微信 event 消息
 * @date: Created in 14:17 2020/6/18
 */
@Service
public class EventMessageServiceImpl implements WeChatMessageService {




    private Logger log = LoggerFactory.getLogger(EventMessageServiceImpl.class);

    private String remark = "逻辑狗家长端APP,加入学校后，可查看宝宝在校动态，接受校园通知，和老师交流互动";

    @Override
    public void afterPropertiesSet()  {
        WeChatMessageFactory.addService("event",this);
        eventMap.put("subscribe",this::subscribeEvent);
        eventMap.put("SCAN",this::scanEvent);
        eventMap.put("LOCATION",this::locationEvent);
        eventMap.put("CLICK",this::clickEvent);
        eventMap.put("VIEW",this::viewEvent);
    }


    @Override
    public String processMessage(Map<String, String> param) {
        String eventType = param.get("Event");
        return eventMap.getOrDefault(eventType,this::unSupportedMessage).apply(param);
    }


    /**
     * 所有事件函数 集合
     */
    private final Map<String, Function<Map<String,String>,String> > eventMap = new HashMap<>();


    /**
     *  subscribe
     * 扫描二维码 （用户未关注时，进行关注后的事件推送）
     * @author wangxiao
     * @date 18:54 2020/7/1
     * @param param
     * @return java.lang.String
     */
    private String subscribeEvent (Map<String,String> param) {
        log.info("********************* param is {}*****************",param.toString());
        String key = param.get("EventKey");
        if (StringUtils.isEmpty(key)) {
            return unSupportedMessage(param);
        }
        key = key.replace("qrscene_","");
        log.info("************** subscribe Received weChat  event key : {} ****************",key);

        return Constants.SUCCESS;
    }


    /**
     *  SCAN
     *  扫描二维码 （用户关注时）
     * @author wangxiao
     * @date 18:54 2020/7/1
     * @param param
     * @return java.lang.String
     */
    private String scanEvent (Map<String,String> param) {
        String key = param.get("EventKey");
        if (StringUtils.isEmpty(key)) {
            return unSupportedMessage(param);
        }
        log.info("************** SCAN Received weChat  event key : {} ****************",key);
        return Constants.SUCCESS;
    }
    
    /**
     *  LOCATION 位置上报
     * @author wangxiao
     * @date 19:09 2020/7/1
     * @param param
     * @return java.lang.String
     */
    private String locationEvent (Map<String,String> param) {
        String latitude = param.get("Latitude");
        String longitude = param.get("Longitude");
        String precision = param.get("Precision");
        log.info("**** LOCATION Received weChat  latitude : {},longitude: {},precision: {} ****",latitude,longitude,precision);
        return Constants.SUCCESS;
    }


    /**
     *  CLICK 自定义菜单
     * @author wangxiao
     * @date 19:09 2020/7/1
     * @param param
     * @return java.lang.String
     */
    private String clickEvent (Map<String,String> param) {
        String key = param.get("EventKey");
        log.info("************** CLICK Received weChat  event key : {} ****************",key);
        return Constants.SUCCESS;
    }


    /**
     *  VIEW 点击菜单跳转链接
     * @author wangxiao
     * @date 19:09 2020/7/1
     * @param param
     * @return java.lang.String
     */
    private String viewEvent (Map<String,String> param) {
        String key = param.get("EventKey");
        log.info("************** VIEW Received weChat  event key : {} ****************",key);
        return Constants.SUCCESS;
    }
}
