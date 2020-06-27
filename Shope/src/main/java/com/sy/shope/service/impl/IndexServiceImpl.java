package com.sy.shope.service.impl;

import com.sy.shope.entity.Category;
import com.sy.shope.entity.Good;
import com.sy.shope.entity.Lottery;
import com.sy.shope.entity.PrizeDomain;
import com.sy.shope.service.facade.*;
import com.sy.shope.support.JsonResult;
import com.sy.shope.support.OrderingException;
import com.sy.shope.tools.Constants;
import com.sy.shope.tools.PrizeUtil;
import com.sy.shope.tools.SystemCacheUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

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

    @Autowired
    private ILotteryService lotteryService;

    @Autowired
    private IPrizeDomainService prizeDomainService;


    /**
     * 首页使用异步加载 暂时只有分类和推荐  推荐算法暂未实现
     * @param userId userId
     * @return JsonResult<Map>
     */
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
               result.put("likeGood", Constants.EMPTY_LIST);
            }
        }

        if (futureCateGory.isDone()) {
            try {
                List<Category> categoryList =  futureCateGory.get();
                result.put("categoryList",categoryList);
            }catch (ExecutionException | InterruptedException e) {
                result.put("categoryList",Constants.EMPTY_LIST);
            }
        }
        return JsonResult.success(result);
    }


    /**
     * 查询推荐商品
     * @return Future
     */
    @Async(value = "asyncExecutor")
    protected Future<List<Good>> queryLikeGoods(String userId) {
        log.info("query user like,user id is :{}",userId);
        List<Good> likeGood = goodService.queryUserLikeGood(userId);
        return new AsyncResult<>(likeGood);
    }

    /**
     * 查询分类
     * @return Future
     */
    @Async(value = "asyncExecutor")
    protected Future<List<Category>> queryCategory () {
        List<Category> categoryList = categoryService.list();
        return new AsyncResult<>(categoryList);
    }


    @Override
    public PrizeDomain lottery(String userId, String lotteryId) {
        Lottery lotteryActivity = getLotteryActivity(lotteryId);
        if (Objects.isNull(lotteryActivity)) {
            throw new OrderingException("500","抽奖活动不存在");
        }
        if (lotteryActivity.isExpire()) {
            throw new OrderingException("500","抽奖活动不存在");
        }
        String prizeDomainIdStr = lotteryActivity.getPrizeDomainIds();

        List<PrizeDomain> prizeDomainList = getPrizeDomainsByIds(prizeDomainIdStr);
        String code = PrizeUtil.calcPrizeCode(prizeDomainList);
        log.warn("user : {} lottery prizeDomain result code :{}",userId,code);
        PrizeDomain result = prizeDomainList.stream().filter(e->code.equals(e.getCode()) && e.getCount() > 0)
                .map(e-> {
                    e.setCount(e.getCount() -1);
                    return e; })
                .findFirst()
                .orElse(PrizeDomain.lucky("-1"));

        /**
         * 缓存移除事件 保存数据库 暂时直接保存
         */
        updatePrizeDomain(prizeDomainIdStr,prizeDomainList);
        return result;
    }

    /**
     * 从缓存中获取 抽奖活动
     * @param id
     * @return Lottery
     */
    private Lottery getLotteryActivity (String id) {
        SystemCacheUtil<Lottery> lotterySystemCache  = SystemCacheUtil.build();
        Lottery lottery = lotterySystemCache.getCache(id);
        if (Objects.isNull(lottery)) {
            lottery = lotteryService.getById(id);
        }
        return lottery;
    }

    /**
     * 获取奖品
     * @param idsStr
     * @return
     */
    private List<PrizeDomain> getPrizeDomainsByIds (String idsStr) {
        List<String> ids = Arrays.stream(idsStr.split(",")).collect(Collectors.toList());
        SystemCacheUtil<List<PrizeDomain>> prizeDomainSystemCache = SystemCacheUtil.build();
        List<PrizeDomain> prizeDomains = prizeDomainSystemCache.getCache(idsStr);
        if (Objects.isNull(prizeDomains)) {
            prizeDomains = prizeDomainService.listByIds(ids);
        }
        return prizeDomains;
    }


    private void updatePrizeDomain (String idsStr,List<PrizeDomain> prizeDomains) {
        SystemCacheUtil<List<PrizeDomain>> prizeDomainSystemCache = SystemCacheUtil.build();
        if (Objects.nonNull(idsStr)) {
            prizeDomainSystemCache.addCache(idsStr,prizeDomains);
        }
        if (Objects.nonNull(prizeDomains) && !prizeDomains.isEmpty()) {
            prizeDomainService.updateBatchById(prizeDomains);
        }
    }

}
