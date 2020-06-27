package com.sy.shope.tools;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 系统常用缓存
 * @author wangxiao
 * @since 1.1
 */
public class SystemCacheUtil<T> {

    Cache<String,T> SYSTEM_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .concurrencyLevel(10)
            .recordStats()
            .build();

    private static SystemCacheUtil instance;



    public static<T> SystemCacheUtil<T> build () {
        if (Objects.isNull(instance)) {
            synchronized (instance) {
                instance = new SystemCacheUtil<>();
            }
        }
        return instance;
    }

    private SystemCacheUtil() {
    }

    public void addCache (String key,T value) {
        SYSTEM_CACHE.put(key,value);
    }

    public   T getCache (String key) {
        return SYSTEM_CACHE.getIfPresent(key);
    }


}
