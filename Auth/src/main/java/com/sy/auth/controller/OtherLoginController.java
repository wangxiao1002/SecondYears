package com.sy.auth.controller;


import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.Cache;
import com.sy.auth.facde.service.WxService;
import com.sy.auth.support.LogDTO;
import com.sy.basis.common.BaseResult;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;


/**
 * 第三方登录
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/login")
public class OtherLoginController {

    @Autowired
    private WxService wxService;


    @GetMapping("/weChat/qrUrl")
    public ResponseEntity<BaseResult<String>> getWeChatQrCodeUrl() throws UnsupportedEncodingException {
        return ResponseEntity.ok(ResultUtil.success(wxService.getWeChatAuthUrl()));
    }

    @GetMapping("/weChat")
    public ResponseEntity<BaseResult<LoginDO>> weChatRedirect(@RequestParam String code,
                                                             @RequestParam String state) {
        LoginDO loginDO = wxService.wxAuth(code);
        return ResponseEntity.ok(ResultUtil.success(loginDO));
    }


}
