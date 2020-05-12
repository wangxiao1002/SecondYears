package com.sy.auth.mapper;

import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单mapper
 * @author wangxiao
 * @since 1.1
 */
@Mapper
public interface MenuMapper {

    /**
     * 通过名字或者code 查询菜单
     * @param other 名称或者code
     * @return List<MenuDTO>
     */
    List<MenuDTO> selectByNameOrCode(@Param("other") String other);

    /**
     * 查询全部
     * @return List<MenuDTO> 菜单集合
     */
    List<MenuDTO> selectAll();


    /**
     * 更新菜单
     * @param menuDTO 新属性 id 和code 不变
     * @return int  受影响的行
     */
    int updateMenu(@Param("param") MenuDTO menuDTO);


    /**
     * 新增菜单
     * @param menuDTO 新属性
     * @return int  受影响的行
     */
    int addMenu(MenuDTO menuDTO);

    /**
     * 删除菜单
     * @param code 新属性
     * @return int  受影响的行
     */
    int delMenu(String code);


    /**
     * 给用户附加菜单
     * @param userCode 用户code
     * @param menuCodes 菜单码集合
     * @return 受影响的行
     */
    int  insertUserMenu(@Param("userCode") String userCode, @Param("array") String ... menuCodes);
}
