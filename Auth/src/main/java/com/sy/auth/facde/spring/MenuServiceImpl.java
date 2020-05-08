package com.sy.auth.facde.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sy.auth.facde.service.MenuService;
import com.sy.auth.mapper.MenuMapper;
import com.sy.basis.domain.MenuDTO;
import com.sy.basis.log.LogEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 * @author wangxiao
 * @since
 */
@Service
public class MenuServiceImpl implements MenuService {


    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<MenuDTO> queryMenuByOther(String other) {
        return menuMapper.selectByNameOrCode(other);
    }

    @Override
    public List<MenuDTO> queryMenus() {
        return menuMapper.selectAll();
    }

    @Override
    public int updateMenuDTO(MenuDTO menuDTO) {
        return menuMapper.updateMenu(menuDTO);
    }

    @Override
    public int addMenuDTO(MenuDTO menuDTO) {
        return menuMapper.addMenu(menuDTO);
    }

    @Override
    public int delMenuDTO(String code) {
        return menuMapper.delMenu(code);
    }

}
