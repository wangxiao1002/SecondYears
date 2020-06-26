package com.sy.shope.service.facade;

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
     * @param userId
     * @return
     */
    JsonResult<Map<String,Object>> queryIndexPage (String userId) ;

}
