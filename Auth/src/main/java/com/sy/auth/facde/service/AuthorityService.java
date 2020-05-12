package com.sy.auth.facde.service;

import com.sy.basis.domain.AuthorityDO;

import java.util.List;

/**
 * 权限
 * @author wangxiao
 * @since 1.1
 */
public interface AuthorityService {

    /**
     * 根据ID 查询权限
     * @param code 代码
     * @return AuthorityDO
     */
    AuthorityDO queryAuthorityByCode(String code);

    /**
     * 根据名称或者代码 模糊查询权限
     * @param other
     * @return List<AuthorityDO>
     */
    List<AuthorityDO> queryAuthorityByOther(String other);

    /**
     *  查询权限集合
     * @return
     */
    List<AuthorityDO> queryAuthorities();

    /**
     * 更新权限
     * @param authorityDO
     * @return 受影响的行
     */
    int updateAuthorityDO(AuthorityDO authorityDO);


    /**
     * 添加权限
     * @param authorityDO
     * @return 受影响的行
     */
    int addAuthorityDO(AuthorityDO authorityDO);

    /**
     * 删除权限
     * @param code 代码
     * @return 受影响的行
     */
    int delAuthorityDO(String code);


}
