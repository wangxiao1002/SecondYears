package com.sy.shope.annation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 本地锁 实现幂等
 * @author wangxiao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LocalLock {

    /**
     * key
     */
    @AliasFor(value = "key")
    String value() default "";

    @AliasFor(value = "value")
    String key() default "";

}
