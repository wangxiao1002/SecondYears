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
    public BaseResult<Integer> addAuthority(@RequestBody AuthorityDO param) {
      return ResultUtil.success(authorityService.addAuthorityDO(param));
    }

    @GetMapping("/{code}")
    public BaseResult<AuthorityDO> getAuthority(@PathVariable String code) {
        return ResultUtil.success(authorityService.queryAuthorityByCode(code)) ;
    }

    @GetMapping
    public BaseResult<List<AuthorityDO>> searchAuthority(@RequestParam String other) {
        return ResultUtil.success(authorityService.queryAuthorityByOther(other)) ;
    }

    @GetMapping("/all")
    public BaseResult<List<AuthorityDO>> getAuthorities() {
        return ResultUtil.success(authorityService.queryAuthorities());
    }

    @DeleteMapping("/{code}")
    public BaseResult<Integer> deleteAuthority(@PathVariable String code) {
        return ResultUtil.success(authorityService.delAuthorityDO(code));
    }

    @PutMapping
    public BaseResult<Integer> updateAuthority(@RequestBody AuthorityDO authorityDO) {
        return ResultUtil.success(authorityService.updateAuthorityDO(authorityDO));
    }

    @GetMapping("/checkAuthority")
    public BaseResult<Boolean> checkAuthority(@RequestParam String userCode,
                                              @RequestParam String uri){
        List<AuthorityDO> authorityDOList = authService.queryAuthorities(userCode);
        List<String> permissions =authorityDOList.stream().map(AuthorityDO::getUri).collect(Collectors.toList());
        return  ResultUtil.success(permissions.stream().anyMatch(s -> s.equals(uri)));
    }



}
