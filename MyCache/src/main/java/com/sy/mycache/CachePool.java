package com.sy.mycache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存池 用来存放缓存对象
 * @author wangxiao
 * @date 2020/4/22
 */
public class CachePool {

    public static ConcurrentHashMap<String,CacheEntity> CACHE_POOL = new ConcurrentHashMap<>();

    private CachePool() {
    }
}
