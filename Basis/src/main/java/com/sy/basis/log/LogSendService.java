package com.sy.basis.log;

import com.sy.basis.common.BaseResult;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.Objects;

/**
 * 推送日志数据接口  其他模块实现该功能即可
 * @author wangxiao
 * @since 1.1
 */
public class LogSendService<T> {

     /**
      * 发送日志
      * @author wangxiao
      * @param logEntity  日志信息
      * @return Boolean 返回结果信息
      **/
     public static  Boolean sendLog (LogEntity logEntity){
          if (Objects.nonNull(logEntity)) {
               return false;
          }
          OkHttpClient okHttpClient = new OkHttpClient();
          RequestBody requestBody = RequestBody.create(logEntity.toJson(),MediaType.parse("application/json;charset=utf-8"));
          Request request = new Request.Builder()
                  .post(requestBody)
                  .url("http://localhost:38094/auth/log/")
                  .build();
          return okHttpClient.newCall(request).isExecuted();
     }
}
