package com.sy.shope.service.facade;



import com.baomidou.mybatisplus.extension.service.IService;
import com.sy.shope.entity.Spec;
import com.sy.shope.entity.SpecGroup;

import java.util.List;

/**
 * @author: wang xiao
 * @description: spec service
 * @date: Created in 17:12 2020/6/3
 */
public interface ISpecService extends IService<Spec> {

    /**
     *  查询产的规格
     * @author: wangxiao
     * @date:  2020/6/3
     * @param goodId  商品
     * @return: java.util.List
     */
    List<SpecGroup> querySpecGroupByGoodId(String goodId);

    /**
     * 保存规则
     * @param specGroupId
     * @param specs
     * @return
     */
    boolean saveSpecList(String specGroupId,List<Spec> specs);


    /**
     * 通过SpecGroupId 删除规格
     * @param ids
     * @return
     */
    boolean delBySpecGroupIds(List<String> ids);
}
