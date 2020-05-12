package com.sy.auth.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.sy.auth.facde.service.AuthService;
import com.sy.basis.common.BaseResult;
import com.sy.basis.common.ResultStatus;
import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.domain.MenuDTO;
import com.sy.basis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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


    /**
     * 查询用户权限点
     * @param userCode 用户
     * @return List<String>
     */
    @GetMapping("/permission/{userCode}")
    public List<String> queryPermission(@PathVariable String userCode) {
        List<AuthorityDO> authorityDOList = authService.queryAuthorities(userCode);
        return authorityDOList.stream().map(AuthorityDO::getUri).collect(Collectors.toList());
    }

    @GetMapping("/user/{userCode}")
    public LoginDO queryUserByUserCode(@PathVariable String userCode){
        return authService.queryUserByCode(userCode);
    }



    public boolean validWithTime(String code) {
        try {
            kaptcha.validate(code,60);
            return true;
        }catch (KaptchaIncorrectException | KaptchaNotFoundException | KaptchaRenderException | KaptchaTimeoutException exception){
            return false;
        }
    }


    @PostMapping("/authority/{userCode}")
    public BaseResult<Integer> accreditAuthority(@PathVariable String userCode,
                                                 @RequestBody List<AuthorityDO> authorityDOList) {
        Assert.notEmpty(authorityDOList,"权限集合不能为空");
        return  ResultUtil.success("添加成功",authService.addUserAuthority(userCode,authorityDOList));
    }


    @PostMapping("/menu/{userCode}")
    public BaseResult<Integer> accreditMenu(@PathVariable String userCode,
                                                 @RequestBody List<MenuDTO> menuDTOList) {
        Assert.notEmpty(menuDTOList,"菜单集合不能为空");
        return  ResultUtil.success("添加成功",authService.addUserMenu(userCode,menuDTOList));
    }







}
