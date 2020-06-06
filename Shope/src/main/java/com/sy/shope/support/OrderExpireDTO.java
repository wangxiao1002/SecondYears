package com.sy.shope.support;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author: wang xiao
 * @description: 延时队列元素
 * @date: Created in 14:18 2020/6/4
 */
@Data
public class OrderExpireDTO<T>  implements Delayed {

    private Long expireTime;
    private T data;

    public OrderExpireDTO(Long expireTime, TimeUnit timeUnit, T data) {
        this.expireTime = TimeUnit.NANOSECONDS.convert(expireTime,timeUnit) + System.nanoTime();
        this.data = data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(expireTime-System.nanoTime(), TimeUnit.NANOSECONDS);

    }

    @Override
    public int compareTo(Delayed other) {
        return Long.compare(getDelay(TimeUnit.NANOSECONDS),other.getDelay(TimeUnit.NANOSECONDS));
    }
}
