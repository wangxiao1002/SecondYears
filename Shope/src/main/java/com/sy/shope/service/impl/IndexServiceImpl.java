package com.sy.shope.service.impl;

import com.sy.shope.entity.Category;
import com.sy.shope.entity.Good;
import com.sy.shope.service.facade.IGoodService;
import com.sy.shope.service.facade.IndexService;
import com.sy.shope.support.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 首页
 * @author wangxiao
 * @since 1.1
 */
@Slf4j
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IGoodService goodService;

    @Autowired
    private ICategoryService categoryService;

    final ArrayList emptyList = new ArrayList(0);

    @Override
    public JsonResult<Map<String,Object>> queryIndexPage(String userId)  {

        Map<String,Object> result = new HashMap<>(6);
        Future<List<Good>> futureGood = queryLikeGoods(userId);
        Future<List<Category>> futureCateGory = queryCategory();
        if (futureGood.isDone()) {
            try {
               List<Good> likeGood =  futureGood.get();
                result.put("likeGood",likeGood);
            }catch (ExecutionException | InterruptedException e) {
               result.put("likeGood",emptyList);
            }
        }

        if (futureCateGory.isDone()) {
            try {
                List<Category> categoryList =  futureCateGory.get();
                result.put("categoryList",categoryList);
            }catch (ExecutionException | InterruptedException e) {
                result.put("categoryList",emptyList);
            }
        }
        return JsonResult.success(result);
    }


    @Async(value = "asyncExecutor")
    protected Future<List<Good>> queryLikeGoods(String userId) {
        log.info("query user like,user id is :{}",userId);
        List<Good> likeGood = goodService.queryUserLikeGood(userId);
        return new AsyncResult<>(likeGood);
    }

    @Async(value = "asyncExecutor")
    protected Future<List<Category>> queryCategory () {
        List<Category> categoryList = categoryService.list();
        return new AsyncResult<>(categoryList);
    }






}
