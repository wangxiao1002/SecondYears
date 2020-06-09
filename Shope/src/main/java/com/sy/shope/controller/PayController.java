package com.sy.shope.controller;

import com.sy.shope.service.facade.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {


    @Autowired
    private WeChatService weChatService;

    @RequestMapping(value = "/wx/notify")
    public String payNotify(HttpServletRequest request) {
        InputStream is = null;
        String xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        try {
            is = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            xmlBack = weChatService.notify(sb.toString());
        } catch (Exception e) {
            log.error("微信手机支付回调通知失败：", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlBack;
    }
}
