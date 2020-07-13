package com.sy.notifyserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 微信公众号
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/weChat")
@Api(value = "WeChatController", tags ="微信")
public class WeChatController {

    private final Logger logger = LoggerFactory.getLogger(WeChatController.class);




    @GetMapping("/receive/{appId}")
    public String addHomeIcon(@PathVariable String appId,
                              @RequestParam(required = false) String signature,
                              @RequestParam(required = false)String timestamp,
                              @RequestParam(required = false) String nonce,
                              @RequestParam(required = false) String echostr) {
        logger.info("********************************{},{},{},{},{}",appId,signature,timestamp,nonce,echostr);

        return WeChatMessageUtil.checkSignature("luojigou", signature, timestamp, nonce) ? echostr : null;
    }




    @PostMapping(value = "/receive/{appId}")
    public String receiveWeChatMessage (@PathVariable String appId, HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> respMap = wxMessageTools.parseXml(request);
        String msgType = respMap.get("MsgType");
        respMap.put("appId",appId);
        WeChatMessageService messageService = WeChatMessageFactory.getService(msgType);
        logger.info("param is {}",respMap.toString());
        if (Objects.isNull(messageService)) {
            logger.error("接受到消息暂无处理类处理：{}",msgType);
            return Constants.FAIL;
        }else {
            return messageService.processMessage(respMap);
        }

    }
}