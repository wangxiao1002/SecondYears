package com.sy.mycache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 定期清除
 * @author wangxiao
 * @date 2020/4/22
 */
public class ExpireThread implements Runnable{

    private Logger logger = LoggerFactory.getLogger(ExpireThread.class);
    private static int count;
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
        logger.info("开始清除缓存,第{}次",count);
        CacheEntity cacheEntity = null;
        for (Map.Entry<String,CacheEntity> entry:  CachePool.CACHE_POOL.entrySet()){
            String key = entry.getKey();
            cacheEntity = entry.getValue();
            long timeOut = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()- cacheEntity.getCreateTime());
            if (cacheEntity.getExpireTime() > timeOut) {
                continue;
            }
            CachePool.CACHE_POOL.remove(key);
        }
        count++;
    }
}
