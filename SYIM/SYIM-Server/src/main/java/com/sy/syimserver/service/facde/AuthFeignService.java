package com.sy.syimserver.service.facde;

import com.sy.basis.domain.LoginDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 查询用户信息
 * @author wangxiao
 * @since 1.1
 */
@FeignClient(value="authApplication",path = "/auth")
public interface AuthFeignService {

    /**
     * 通过用户名称查询用户是否存在
     * @param userCode
     * @return UserDto
     */
    @GetMapping("/user/{userCode}")
    LoginDO queryUserByUserCode(@PathVariable String userCode);
}
