package com.sy.basis.cache;


import com.sy.basis.util.StringUtil;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 * @author wangxiao
 * @since 1.1
 */
public class CacheUtil {

     /**
      * 存值
      * @Author wangxiao
      * @param key key
      * @param value value
      **/
    public static <T> void put(String key,T value){
      if (StringUtil.isEmpty(key)){
          return;
      }
      CacheEntity cacheEntity;
      long nowTime = System.currentTimeMillis();
      if (CacheManager.CACHE_POOL.containsKey(key)) {
          cacheEntity = CacheManager.CACHE_POOL.get(key);
          cacheEntity.setValue(value);
          cacheEntity.setCreateTime(nowTime);
          cacheEntity.setExpireTime(CacheManager.DEFAULT_EXPIRE_TIME);
          return;
      }
      cacheEntity = new CacheEntity<T>(key,value,nowTime,CacheManager.DEFAULT_EXPIRE_TIME);
      CacheManager.CACHE_POOL.put(key,cacheEntity);
    }
     /**
      * 存值
      * @author wangxiao
      * @param key key
      * @param value value
      * @param expireTime 超时时间 秒
      **/
    public static void put(String key,Object value,long expireTime) {
        if (StringUtil.isBlank(key)){
            return;
        }
        CacheEntity cacheEntity ;
        if (CacheManager.CACHE_POOL.containsKey(key)) {
            cacheEntity = CacheManager.CACHE_POOL.get(key);
            cacheEntity.setValue(value);
            cacheEntity.setCreateTime(System.currentTimeMillis());
            cacheEntity.setExpireTime(expireTime);
        }else {
            cacheEntity = new CacheEntity(key,value,expireTime);
            cacheEntity.setCreateTime(System.currentTimeMillis());
        }

    }

      /**
       * 获取值
       * @author wangxiao
       * @param key key
       * @return value value
       **/
    public static <T> T get(String key) {
        if (StringUtil.isBlank(key)){
            return null;
        }
        if (CacheManager.CACHE_POOL.isEmpty()) {
            return null;
        }
        if (! CacheManager.CACHE_POOL.containsKey(key)) {
            return null;
        }
        CacheEntity<T> cacheEntity = CacheManager.CACHE_POOL.get(key);
        if (isExpireTime(cacheEntity.getCreateTime(),cacheEntity.getExpireTime())) {
            return null;
        }
        cacheEntity.setHitCount(cacheEntity.getHitCount() + 1);
        return cacheEntity.getValue();
    }

     /**
      * 是否超时
      * @author wangxiao
      * @param
      * @return
      **/
    public static boolean isExpire(String key)  {
        if (StringUtil.isBlank(key)){
            return true;
        }
        if (CacheManager.CACHE_POOL.isEmpty()) {
            return true;
        }
        if (! CacheManager.CACHE_POOL.containsKey(key)) {
            return true;
        }
        CacheEntity<?> cacheEntity = CacheManager.CACHE_POOL.get(key);
        return isExpireTime(cacheEntity.getCreateTime(),cacheEntity.getExpireTime());
    }

     /**
      * 判断是否包含key
      * @author wangxiao
      * @param key key
      * @return boolean 是否包含
      **/
    public static boolean containsKey(String key) {
        if (StringUtil.isBlank(key)){
            return false;
        }
        if (CacheManager.CACHE_POOL.isEmpty()) {
            return false;
        }
        return CacheManager.CACHE_POOL.containsKey(key);
    }


    private static boolean isExpireTime(long createTime,long expireTime) {
        long timeOut = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - createTime);
        if (expireTime <= timeOut) {
            return true;
        }
        return false;
    }
}
