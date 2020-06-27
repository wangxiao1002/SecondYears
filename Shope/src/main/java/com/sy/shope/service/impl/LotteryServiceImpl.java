package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Lottery;
import com.sy.shope.mappers.LotteryMapper;
import com.sy.shope.service.facade.ILotteryService;
import org.springframework.stereotype.Service;

/**
 * LotteryServiceImpl
 * @author wangxiao
 * @since 1.1
 */
@Service
public class LotteryServiceImpl extends ServiceImpl<LotteryMapper, Lottery> implements ILotteryService {
}
