package com.sy.auth.facde.service;

import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.domain.MenuDTO;

import java.util.List;

/**
 *
 * @author wangxiao
 */
public interface AuthService {


    /**
     * 使用 登录信息查询登录用户信息
     * @param param
     * @return LoginDO
     */
    LoginDO queryUserByInfo(LoginDO param);


    /**
     * 使用 用户code 查询用户
     * @param userCode
     * @return LoginDO
     */
    LoginDO queryUserByCode(String userCode);

     /**
      * 查询用户菜单
      * @author wangxiao
      * @param userCode  用户code
      * @return List<MenuDTO> 菜单集合
      **/
    List<MenuDTO> queryMenus(String userCode);




    /**
     * 拆线呢用户权限点
     * @param userCode
     * @return List<AuthorityDO>
     */
    List<AuthorityDO> queryAuthorities(String userCode);

    /**
     * 给用户添加权限
     * @param userCode 用户
     * @param authorityDOS 权限集合
     * @return 受影响的行
     */
    int addUserAuthority (String userCode,List<AuthorityDO> authorityDOS);


    /**
     * 给用户添加菜单
     * @param userCode 用户
     * @param menuDTOS 菜单集合
     * @return 受影响的行
     */
    int addUserMenu (String userCode,List<MenuDTO> menuDTOS);



}
