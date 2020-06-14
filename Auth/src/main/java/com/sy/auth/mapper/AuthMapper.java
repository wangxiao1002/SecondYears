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
     * 验证用户是否存在
     * @param userCode
     * @return LoginDO
     */
    @ResultMap(value = "userResultMap")
    @Select("SELECT t.code,t.label,t.phone_number FROM t_auth_user t WHERE t.`code` =#{param}")
    LoginDO  selectUserByCode(@Param("param") String userCode);

    /**
     * 查询用户菜单
     * @param userCode
     * @return List<MenuDTO>
     */
    @Cacheable(cacheNames = "AUTH_OTHER_INFO",keyGenerator = "authKeyGenerator")
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
    @Cacheable(cacheNames ="AUTH_USER_INFO",keyGenerator = "authorityKeyGenerator")
    @Results(id = "authorityResultMap",value = {
            @Result(property = "label",column = "label"),
            @Result(property = "code",column = "code"),
            @Result(property = "uri",column = "uri")
    })
    @Select("SELECT ao.`code`,ao.label,ao.uri FROM t_auth_authority  ao LEFT JOIN t_auth_user_authority uao ON ao.`code` = uao.authority_code WHERE uao.user_code =#{userCode}")
    List<AuthorityDO> selectAuthorityByUser(@Param("userCode") String userCode);


    /**
     * 查询微信用户
     * @param openId
     * @return
     */
    @ResultMap(value = "userResultMap" )
    @Select("SELECT t.code,t.label,t.phone_number FROM t_auth_user t WHERE t.code = (SELECT w.user_code FROM t_auth_user_wechat w WHERE w.open_id = #{openId} )")
    LoginDO selectUserByOpenId(@Param("openId") String openId);


    /**
     * insert
     * @param loginDO
     * @return
     */
    @Insert({"<script>",
            "INSERT INTO t_auth_user (" +
                    "<if test='param.code!=null'>"+
                    "`code`," +
                    "</if>" +
                    "<if test='param.password!=null'>"+
                    "`password`," +
                    "</if>" +
                    "<if test='param.phoneNumber!=null'>"+
                    "phone_number," +
                    "</if>" +
                    "<if test='param.label!=null'>"+
                    "label," +
                    "</if>" +
                    "create_date) VALUES (" +
                    "<if test='param.code!=null'>"+
                    "#{param.code}," +
                    "</if>" +
                    "<if test='param.password!=null'>"+
                    "#{param.password}," +
                    "</if>" +
                    "<if test='param.phoneNumber!=null'>"+
                    "#{param.phoneNumber}," +
                    "</if>" +
                    "<if test='param.label!=null'>"+
                    "#{param.label}," +
                    "</if>" +
                    "now ()"+
                    ")",
            "</script>"})
    int insertLoginDto(@Param("param") LoginDO loginDO);


    /**
     * 保存用户openId
     * @param userCode
     * @param openId
     * @return
     */
    @Insert("INSERT INTO t_auth_user_wechat (user_code,open_id) VALUES (#{userCode},#{openId})")
    int insertUserOpenId(String userCode,String openId);

}
