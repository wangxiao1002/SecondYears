package com.sy.shope.annation;

import com.sy.shope.support.LimitType;

/**
 * 注解限流
 * @author wangxiao
 */
public @interface Limit {

    /**
     * key
     * */
    String key () default "";
    /**
     * 时间段
     * */
    int period ();
    /**
     * 次数
     * */
    int count();

    LimitType limitType() default LimitType.CUSTOMER;

}
