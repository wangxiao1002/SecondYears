package com.sy.shope.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Good;
import com.sy.shope.mappers.GoodMapper;
import com.sy.shope.service.facade.IGoodService;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @author wangxiao
 * @since
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>  implements IGoodService {
}
