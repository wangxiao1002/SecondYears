package com.sy.mycache;

import org.apache.commons.lang3.StringUtils;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 * @author wangxiao
 * @date 2020/4/22
 */
public class CacheUtil {

    public static void put(String key,Object value,long expireTime) {
        if (StringUtils.isBlank(key)){
            return;
        }
        CacheEntity cacheEntity ;
        if (CachePool.CACHE_POOL.containsKey(key)) {
            cacheEntity = CachePool.CACHE_POOL.get(key);
            cacheEntity.setValue(value);
            cacheEntity.setCreateTime(System.currentTimeMillis());
            cacheEntity.setExpireTime(expireTime);
        }else {
            cacheEntity = new CacheEntity(key,value,expireTime);
            cacheEntity.setCreateTime(System.currentTimeMillis());
        }

    }

    public static Object get(String key) throws CacheException{
        if (StringUtils.isBlank(key)){
            throw new CacheException("key is not blank");
        }
        if (CachePool.CACHE_POOL.isEmpty()) {
            throw new CacheException("cache pool is empty, hasn`t this key");
        }
        if (!CachePool.CACHE_POOL.containsKey(key)) {
            throw new CacheException("cache pool  hasn`t this key");
        }
        CacheEntity cacheEntity = CachePool.CACHE_POOL.get(key);
        long timeOut = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cacheEntity.getCreateTime());
        if (cacheEntity.getExpireTime() <= timeOut){
            throw new CacheException("cache is expire Time out");
        }
        CacheEntity entity = CachePool.CACHE_POOL.get(key);
        entity.setHitCount(entity.getHitCount()+1);
        return entity.getValue();
    }

    public static boolean isExpire(String key)  {
        if (StringUtils.isBlank(key)){
            return true;
        }
        if (CachePool.CACHE_POOL.isEmpty()) {
            return true;
        }
        if (!CachePool.CACHE_POOL.containsKey(key)) {
            return true;
        }
        CacheEntity cacheEntity = CachePool.CACHE_POOL.get(key);
        long timeOut = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cacheEntity.getCreateTime());
        if (cacheEntity.getExpireTime() <= timeOut){
            return true;
        }
        return false;
    }

    public static boolean containsKey(String key) throws CacheException{
        if (StringUtils.isBlank(key)){
            throw new CacheException("key must not blank");
        }
        if (CachePool.CACHE_POOL.isEmpty()) {
            return false;
        }
        return CachePool.CACHE_POOL.containsKey(key);
    }


}
