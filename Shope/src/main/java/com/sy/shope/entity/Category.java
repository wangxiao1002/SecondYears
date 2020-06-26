package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 分类
 * @author wangxiao
 * @since 1.1
 */
@Data
@TableName("t_shope_category")
public class Category {

    @TableId
    private String id;

    private String name;

    private String parentId;

    private Integer sort;

    @TableField(exist = false)
    private List<Good> goodList;

    @TableField(exist = false)
    private List<Category> childrenList;
}
