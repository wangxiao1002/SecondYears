package com.sy.shope.mappers;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroupVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author: wang xiao
 * @description: spec Mapper
 * @date: Created in 17:18 2020/6/3
 */
public interface SpecMapper extends BaseMapper<Spec> {
    /**
     *  查询商品规格
     * @author: wangxiao
     * @date: 17:20 2020/6/3
     * @param goodId goodId
     * @return: java.util.List
     */
    @Results(id = "specMap",value = {
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="id",property="specList",many=@Many(select="com.sy.shope.mappers.SpecMapper.selectSpecByGroupId",fetchType= FetchType.EAGER))
    })
    @Select("SELECT sg.id,sg.`name` FROM t_shope_spec_group sg  WHERE sg.spu_id = #{goodId}")
    List<SpecGroupVO> selectSpecGroupByGoodId(String goodId);


    /**
     *  查询规格通过规格组Id
     * @author: wangxiao
     * @date: 17:35 2020/6/3
     * @param groupId 分组ID
     * @return: java.util.List
     */
    @Results(id = "specBaseMap",value = {
            @Result(id=true,column="id",property="id"),
            @Result(column="name",property="name"),
            @Result(column="spec_group_id",property="specGroupId"),
            @Result(column = "price",property ="price" )
    })
    @Select("SELECT s.id,s.`name`,s.price,s.spec_group_id FROM t_shope_spec s WHERE s.spec_group_id =#{groupId}")
    List<Spec> selectSpecByGroupId(String groupId);
}
