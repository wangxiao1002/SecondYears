package com.sy.shope.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sy.shope.tools.Constants;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;


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


    public boolean isExpire () {
        LocalDateTime startTime = LocalDateTime.parse(this.startTime, Constants.DATE_TIME_FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(this.endTime,Constants.DATE_TIME_FORMATTER);
        LocalDateTime nowTime = LocalDateTime.now(ZoneId.systemDefault());
        if (nowTime.isAfter(startTime) && nowTime.isBefore(endTime)) {
            return true;
        }
        return false;
    }
}
