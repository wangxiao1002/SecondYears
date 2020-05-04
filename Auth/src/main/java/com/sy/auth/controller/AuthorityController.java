package com.sy.auth.controller;

import com.sy.auth.facde.service.AuthService;
import com.sy.auth.facde.service.AuthorityService;
import com.sy.basis.common.BaseResult;
import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限点控制
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthorityService authorityService;


    @PostMapping
    public BaseResult addAuthority(@RequestBody AuthorityDO param) {
      return ResultUtil.success(authorityService.addAuthorityDO(param));
    }

    @GetMapping("/{id}")
    public BaseResult<AuthorityDO> getAuthority(@PathVariable String id) {
        return ResultUtil.success(authorityService.queryAuthorityByCode(id)) ;
    }

    @GetMapping
    public BaseResult<List<AuthorityDO>> getAuthorities() {
        return ResultUtil.success(authorityService.queryAuthorities());
    }

    @GetMapping("/checkAuthority")
    public BaseResult<Boolean> checkAuthority(@RequestParam String userCode,
                                              @RequestParam String uri){
        List<AuthorityDO> authorityDOList = authService.queryAuthorities(userCode);
        List<String> permissions =authorityDOList.stream().map(AuthorityDO::getUri).collect(Collectors.toList());
        return  ResultUtil.success(permissions.stream().anyMatch(s -> s.equals(uri)));
    }

}
