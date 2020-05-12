package com.sy.auth.controller;

import com.sy.auth.facde.service.MenuService;
import com.sy.basis.common.BaseResult;
import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.MenuDTO;
import com.sy.basis.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单的crud
 * @author wangxiao
 * @since 1.1
 */
@RestController
@RequestMapping("/menu")
public class MenuController {


    @Autowired
    private MenuService menuService;



    @PostMapping
    public BaseResult<Integer> addAuthority(@RequestBody MenuDTO param) {
        return ResultUtil.success(menuService.addMenuDTO(param));
    }

    @GetMapping
    public BaseResult<List<MenuDTO>> searchAuthority(@RequestParam String other) {
        return ResultUtil.success(menuService.queryMenuByOther(other)) ;
    }

    @GetMapping("/all")
    public BaseResult<List<MenuDTO>> getAuthorities() {
        return ResultUtil.success(menuService.queryMenus());
    }

    @DeleteMapping("/{code}")
    public BaseResult<Integer> deleteAuthority(@PathVariable String code) {
        return ResultUtil.success(menuService.delMenuDTO(code));
    }

    @PutMapping
    public BaseResult<Integer> updateAuthority(@RequestBody MenuDTO menuDTO) {
        return ResultUtil.success(menuService.updateMenuDTO(menuDTO));
    }
}
