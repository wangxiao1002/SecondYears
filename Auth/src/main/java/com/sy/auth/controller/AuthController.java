package com.sy.auth.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.sy.basis.common.BaseResult;
import com.sy.basis.common.ResultStatus;
import com.sy.basis.domain.LoginDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 * 登录
 * @author wangxiao
 * @since 1.1
 */
@RestController
public class AuthController {



    private  Kaptcha kaptcha;

    @Autowired
    public void setKaptcha(Kaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    @PostMapping("/authLogin")
    public BaseResult authLogin(@RequestBody LoginDO loginDO) {
        return null;
    }

    @GetMapping(value = "/get")
    public void render() {
        kaptcha.render();
    }


    public String validWithTime(String code) {

        try {
            kaptcha.validate(code,60);
            return ResultStatus.SUCCESS;
        }catch (KaptchaIncorrectException | KaptchaNotFoundException | KaptchaRenderException | KaptchaTimeoutException exception){
            return ResultStatus.FAIL;
        }
    }



}
