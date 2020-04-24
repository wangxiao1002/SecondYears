package com.sy.basis.log;

import com.sy.basis.common.BaseResult;

/**
 * 推送日志数据接口  其他模块实现该功能即可
 * @author wangxiao
 * @since 1.1
 */
public interface LogSendService<T> {

     /**
      * 发送日志
      * @author wangxiao
      * @param logEntity  日志信息
      * @return BaseResult 返回结果信息
      **/
     BaseResult<T> sendLog (LogEntity logEntity);
}
