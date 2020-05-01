package com.sy.auth.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.sy.basis.common.BaseResult;
import com.sy.basis.common.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码
 * @author wangxiao
 * @since
 */
@RestController
@RequestMapping(value = "/verifyCode")
public class VerifyCodeController {


    private final Kaptcha kaptcha;

    @Autowired
    public VerifyCodeController(Kaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    @GetMapping(value = "/get")
    public void render() {
        kaptcha.render();
    }


    public BaseResult validWithTime(String code) {
        BaseResult result=new BaseResult();
        try {
            kaptcha.validate(code,60);
            result.status(ResultStatus.SUCCESS);
        }catch (KaptchaIncorrectException | KaptchaNotFoundException | KaptchaRenderException | KaptchaTimeoutException  exception){
            result.status(ResultStatus.FAIL);
        }
        return result;
    }

}
