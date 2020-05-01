package com.sy.auth.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.sy.auth.facde.service.AuthService;
import com.sy.basis.common.BaseResult;
import com.sy.basis.common.ResultStatus;
import com.sy.basis.domain.LoginDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * 登录
 * @author wangxiao
 * @since 1.1
 */
@RestController
public class AuthController {


    private  Kaptcha kaptcha;


    @Autowired
    private AuthService authService;

    @Autowired
    public void setKaptcha(Kaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    @PostMapping("/authLogin")
    public BaseResult<Object> authLogin(@RequestBody LoginDO loginDO) {
        BaseResult<Object> response = new BaseResult<>();
        if (!validWithTime(loginDO.getVerifyCode())) {
            return new BaseResult<>().status(ResultStatus.FAIL).message("验证码过期");
        }

        LoginDO tempLoginDO = authService.queryUserByInfo(loginDO);
        Optional.ofNullable(tempLoginDO)
                .map(temp -> response.result(authService.queryMenus(temp.getCode())))
                .orElse(response.status(ResultStatus.FAIL).message("用户密码不正确"));
        return response;
    }

    @GetMapping(value = "/verifyCode")
    public void render() {
        kaptcha.render();
    }


    public boolean validWithTime(String code) {
        try {
            kaptcha.validate(code,60);
            return true;
        }catch (KaptchaIncorrectException | KaptchaNotFoundException | KaptchaRenderException | KaptchaTimeoutException exception){
            return false;
        }
    }



}
