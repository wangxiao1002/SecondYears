package com.sy.shope.service.facade;

import com.sy.shope.entity.PrizeDomain;
import com.sy.shope.support.JsonResult;
import java.util.Map;


/**
 * 首页
 * @author wangxiao
 * @since 1.1
 */
public interface IndexService {

    /**
     * 查询首页
     * @param userId userId
     * @return JsonResult
     */
    JsonResult<Map<String,Object>> queryIndexPage (String userId) ;


    /**
     * 抽奖
     * @param userId usrId
     * @param lotteryId 抽奖活动Id
     * @return PrizeDomain
     */
    PrizeDomain lottery (String userId, String lotteryId);

}
