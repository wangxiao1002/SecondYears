package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.PrizeDomain;
import com.sy.shope.mappers.PrizeDomainMapper;
import com.sy.shope.service.facade.IPrizeDomainService;
import org.springframework.stereotype.Service;

/**
 * PrizeDomainServiceImpl
 * @author wangxiao
 * @since 1.1
 */
@Service
public class PrizeDomainServiceImpl extends ServiceImpl<PrizeDomainMapper, PrizeDomain> implements IPrizeDomainService {
}
