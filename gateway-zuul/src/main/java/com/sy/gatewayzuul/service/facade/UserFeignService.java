package com.sy.gatewayzuul.service.facade;

import com.sy.basis.domain.LoginDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

 /**
  * @Author wangxiao
  **/

 @FeignClient(value="authApplication",path = "/auth")
public interface UserFeignService {

    /**
     * 通过用户名称查询用户是否存在
     * @param userCode
     * @return UserDto
     */
    @GetMapping("/user/{userCode}")
    LoginDO queryUserByUserCode(@PathVariable String userCode);

    /**
     * 通过用户名称查询权限
     * @param userCode
     * @return List<String>
     */
    @GetMapping("/permission/{userCode}")
     List<String> queryUserPermissions(@PathVariable String userCode);
}
