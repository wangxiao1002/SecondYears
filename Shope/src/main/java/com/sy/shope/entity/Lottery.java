package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * 抽奖活动
 * @author wangxiao
 * @since 1.1
 */
@Data
@TableName("t_shope_lottery")
public class Lottery {

    @TableId
    private String id;

    private String label;

    private String prizeDomainIds;

    private String startTime;

    private String endTime;

    @TableField(exist = false)
    private List<PrizeDomain> prizeDomains;
}
