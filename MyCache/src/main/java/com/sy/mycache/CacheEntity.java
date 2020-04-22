package com.sy.mycache;



/**
 * 缓存实体类
 * @author wangxiao
 * @date 2020/4/22
 */
public class CacheEntity  implements Comparable<CacheEntity> {

    /**
     * key
     */
    private String key;

    /**
     * value
     */
    private Object value;

    /**
     * 存活时间
     */
    private long expireTime;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 命中次数
     */
    private int hitCount;


    public CacheEntity() {
    }

    public CacheEntity(String key, Object value, long expireTime) {
        this.key = key;
        this.value = value;
        this.expireTime = expireTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }


    @Override
    public int compareTo(CacheEntity o) {
        return Integer.compare(hitCount, o.hitCount);
    }




}
