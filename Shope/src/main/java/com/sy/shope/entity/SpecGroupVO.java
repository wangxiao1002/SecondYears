package com.sy.shope.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author: wang xiao
 * @description: 规格组 view
 * @date: Created in 16:57 2020/6/3
 */
@Data
@TableName("t_shope_spec_group")
public class SpecGroupVO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    @TableField(exist = false)
    private List<Spec> specList;

}
