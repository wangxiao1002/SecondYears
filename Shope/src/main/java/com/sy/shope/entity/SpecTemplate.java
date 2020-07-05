package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Spec 模板
 * @author wangxiao
 * @since 1.1
 */
@Data
@TableName("t_shope_spec_template")
public class SpecTemplate {

    @TableId
    private String id;

    private String label;

    /**
     * 数据库字段JSON 数组
     */
    private String values;
}
