package com.sy.shope.service.facade;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.SpecGroup;

import java.util.List;

/**
 * TODO
 *ISpecGroupService
 * @author wangxiao
 * @since 1.1
 */
public interface ISpecGroupService extends IService<SpecGroup> {

    /**
     * 保存商品规格
     * @param goodId
     * @param specGroups
     * @return is Success
     */
    boolean saveSpecGroup (String goodId,List<SpecGroup> specGroups);


    /**
     * 删除商品规格组
     * @param goodId
     * @return
     */
    boolean delSpecGroup (String goodId);
}
