package com.sy.auth.controller;


import com.sy.auth.facde.service.WxService;
import com.sy.basis.common.BaseResult;
import com.sy.basis.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

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

    @GetMapping("/wx")
    public BaseResult weixinLogin(@RequestParam String code, HttpServletRequest request,
                                  HttpServletResponse response) {


        return null;
    }
}
