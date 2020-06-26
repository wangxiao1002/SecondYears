package com.sy.shope.service.facade;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sy.shope.entity.Category;
import com.sy.shope.mappers.CateGoryMapper;
import com.sy.shope.service.impl.ICategoryService;
import org.springframework.stereotype.Service;

/**
 * CategoryServiceImpl
 * @author wangxiao
 * @since 1.1
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CateGoryMapper, Category> implements ICategoryService {
}
