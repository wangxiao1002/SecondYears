package com.sy.shope.service.facade;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.Good;

import java.util.List;

/**
 * 
 * @author wangxiao
 * @since 1.1
 */
public interface IGoodService extends IService<Good> {
    /**
     *  分页查询查询商品
     * @param current 当前页
     * @return Page<Good>
     */
    Page<Good> queryOrderPage (int current);

    /**
     * 查看商品详情
     * @param goodId 商品id
     * @return Good
     */
    Good queryGoodDetails(String goodId);


    /**
     * 查询用户喜欢，推荐
     * @param userId
     * @return
     */
    List<Good> queryUserLikeGood(String userId);
}
