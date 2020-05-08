package com.sy.auth.mapper;

import com.sy.basis.domain.AuthorityDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 权限
 * @author wangxiao
 * @since 1.1
 */
@Mapper
public interface AuthorityMapper {

    /**
     * 通过id 查询权限
     * @param code id
     * @return AuthorityDO
     */
    AuthorityDO selectByCode(@Param("code") String code);


    /**
     * 通过名字或者code 查询权限
     * @param other 名称或者code
     * @return List<AuthorityDO>
     */
    List<AuthorityDO> selectByNameOrCode(@Param("other") String other);

    /**
     * 查询全部
     * @return List<AuthorityDO> 权限集合
     */
    List<AuthorityDO> selectAll();


    /**
     * 更新权限
     * @param authorityDO 新属性 id 和code 不变
     * @return int  受影响的行
     */
    int updateAuthorityDO(@Param("param") AuthorityDO authorityDO);


    /**
     * 新增权限
     * @param authorityDO 新属性
     * @return int  受影响的行
     */
    int addAuthorityDO(AuthorityDO authorityDO);

    /**
     * 删除权限
     * @param code 新属性
     * @return int  受影响的行
     */
    int delAuthorityDO(String code);
}
