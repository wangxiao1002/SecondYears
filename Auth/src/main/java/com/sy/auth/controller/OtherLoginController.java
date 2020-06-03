package com.sy.auth.controller;


import com.sy.basis.common.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方登录
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/login")
public class OtherLoginController {

    @GetMapping("/wx")
    public BaseResult weixinLogin(@RequestParam String code, HttpServletRequest request,
                                  HttpServletResponse response) {
        if (code == null) {
            
        }
        return null;
    }
}
