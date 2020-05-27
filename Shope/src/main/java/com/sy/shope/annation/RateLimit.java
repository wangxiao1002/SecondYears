package com.sy.shope.annation;

import java.util.concurrent.TimeUnit;

/**
 * 注解限流
 * @author wangxiao
 */
public @interface RateLimit {

    /**
     * 每秒向桶放入令牌的数量
     */
    double perSecond() default 100;
    /**
     * 获取令牌的等待时间
     *
     * @return
     */
    int timeOut() default 0;
    /**
     * 超时时间单位
     *
     * @return
     */
    TimeUnit timeOutUnit() default  TimeUnit.MILLISECONDS;

}
