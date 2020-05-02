package com.sy.auth.mapper;

import com.sy.basis.domain.AuthorityDO;
import com.sy.basis.domain.LoginDO;
import com.sy.basis.domain.MenuDTO;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 用户信息 权限查询
 * @author wangxiao
 * @since 1.1
 */
@Mapper
public interface AuthMapper {


    /**
     * 验证用户是否存在
     * @param loginDO
     * @return LoginDO
     */
    @Results(id = "userResultMap",value = {
            @Result(property = "label",column = "label"),
            @Result(property = "code",column = "code"),
            @Result(property = "phoneNumber",column = "phone_number")
    })
    @Select("SELECT t.code,t.label,t.phone_number FROM t_auth_user t WHERE t.`code` =#{param.code} AND t.`password` =#{param.password}")
    LoginDO  selectUser(@Param("param") LoginDO loginDO);


    /**
     * 查询用户菜单
     * @param userCode
     * @return List<MenuDTO>
     */
    @Results(id = "menuResultMap",value = {
            @Result(property = "label",column = "label"),
            @Result(property = "code",column = "code"),
            @Result(property = "url",column = "url")
    })
    @Select("SELECT m.`code`,m.label,m.url FROM t_auth_menu m LEFT JOIN t_auth_user_menu um ON um.menu_code = m.`code` WHERE um.user_code =#{userCode}")
    List<MenuDTO> selectMenusByUser(@Param("userCode") String userCode);


    /**
     * 查询用户权限列表
     * @param userCode
     * @return List<AuthorityDO>
     */
    @Cacheable(cacheNames ="AUTH_USER_INFO",key = "authorityKeyGenerator")
    @Results(id = "authorityResultMap",value = {
            @Result(property = "label",column = "label"),
            @Result(property = "code",column = "code"),
            @Result(property = "uri",column = "uri")
    })
    @Select("SELECT ao.`code`,ao.label,ao.uri FROM t_auth_authority  ao LEFT JOIN t_auth_user_authority uao ON ao.`code` = uao.authority_code WHERE uao.user_code =#{userCode}")
    List<AuthorityDO> selectAuthorityByUser(@Param("userCode") String userCode);


}
