package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;



/**
 * @author: wang xiao
 * @description: 商品
 * @date: Created in 13:31 2020/6/3
 */
@Data
@TableName("t_shope_spu")
@Document(indexName = "goods")
public class Good {

    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word" )
    private String spuNameZh;

    @Field(type = FieldType.Text,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word" )
    private String spuNameEn;

    @NotNull(message = "价格不能为空")
    @Field(type = FieldType.Double,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word" )
    private BigDecimal price;


    private String imageUrl;

    private String state;

    private String categoryId;

    @Field(type = FieldType.Text,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word" )
    private String spuDesc;

    @Field(type = FieldType.Date,analyzer = "ik_max_word" ,searchAnalyzer="ik_max_word" )
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String createTime;


    private String updateTime;
    @TableField(exist = false)
    private List<SpecGroupVO> specGroupVOS;

}
