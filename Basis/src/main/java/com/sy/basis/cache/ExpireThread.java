package com.sy.basis.cache;



import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定期清除
 * @author wangxiao
 * @date 2020/4/22
 */
public class ExpireThread implements Runnable{
    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10L);
                cleanCache();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    private void cleanCache() {
        CacheEntity<?> cacheEntity = null;
        for (Map.Entry<String,CacheEntity> entry:  CacheManager.CACHE_POOL.entrySet()){
            String key = entry.getKey();
            cacheEntity = entry.getValue();
            long timeOut = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()- cacheEntity.getCreateTime());
            if (cacheEntity.getExpireTime() > timeOut) {
                continue;
            }
            CacheManager.CACHE_POOL.remove(key);
        }

    }
}
