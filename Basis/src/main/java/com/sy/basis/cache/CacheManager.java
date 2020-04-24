package com.sy.basis.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统缓存管理器
 * @author wangxiao
 * @since 1.1
 */
public class CacheManager {

    protected static ConcurrentHashMap<String,CacheEntity<?>> CACHE_POOL = new ConcurrentHashMap<>(100);

    protected final static long DEFAULT_EXPIRE_TIME = 10000L;
}
