package com.sy.basis.cache;

import java.io.Serializable;

/**
 * 缓存实体
 * @author wangxiao
 * @since 1.1
 */
public class CacheEntity<T> implements Serializable,Comparable<CacheEntity<T>> {

    private String key;

    private T value;

    private long createTime;

    private long expireTime;

    private int hitCount;


    public CacheEntity() {
    }

    public CacheEntity(String key, T value, long expireTime) {
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

    public CacheEntity(String key, T value, long createTime, long expireTime) {
        this.key = key;
        this.value = value;
        this.createTime = createTime;
        this.expireTime = expireTime;
    }

    public CacheEntity<T> createTime(long createTime) {
        this.createTime = createTime;
        return this;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    @Override
    public int compareTo(CacheEntity other) {
        return Integer.compare(hitCount,other.hitCount);
    }
}
