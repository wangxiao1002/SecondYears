package com.sy.auth.facde.service;

import com.sy.basis.domain.MenuDTO;

import java.util.List;

/**
 * 菜单
 * @author wangxiao
 * @since
 */
public interface MenuService {


    /**
     * 根据名称或者代码 模糊查询菜单
     * @param other
     * @return List<MenuDTO>
     */
    List<MenuDTO> queryMenuByOther(String other);

    /**
     *  查询菜单集合
     * @return
     */
    List<MenuDTO> queryMenus();

    /**
     * 更新菜单
     * @param menuDTO
     * @return 受影响的行
     */
    int updateMenuDTO(MenuDTO menuDTO);


    /**
     * 添加菜单
     * @param menuDTO
     * @return 受影响的行
     */
    int addMenuDTO(MenuDTO menuDTO);

    /**
     * 添加菜单
     * @param code 代码
     * @return 受影响的行
     */
    int delMenuDTO(String code);
}
