package com.sy.shope.controller;

import com.sy.shope.service.facade.WeChatService;
import com.sy.shope.tools.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping(value = "/wx/notify",produces = MediaType.APPLICATION_XML_VALUE)
    public String payNotify(HttpServletRequest request) {
        InputStream is;
        String xmlBack ;
        try {
            is = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            xmlBack = weChatService.notify(sb.toString());
            return xmlBack;
        } catch (Exception e) {
            xmlBack = Constants.FAIL_XML;
            log.error("微信手机支付回调通知失败：", e);
            return xmlBack;
        }

    }
}
