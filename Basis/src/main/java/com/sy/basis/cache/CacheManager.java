package com.sy.basis.cache;

import java.util.concurrent.*;

/**
 * 系统缓存管理器
 * @author wangxiao
 * @since 1.1
 */
public class CacheManager {

    protected static ConcurrentHashMap<String,CacheEntity> CACHE_POOL = new ConcurrentHashMap<>(100);

    protected final static long DEFAULT_EXPIRE_TIME = 1000*60L;

    static {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExpireThread expireThread = new ExpireThread();
        executorService.execute(expireThread);
    }


    public static void main(String[] args) {
        CacheUtil.put("a","hello");
        System.out.print(CacheUtil.containsKey("a"));
        System.out.print(""+ CacheUtil.get("a"));
    }
}
