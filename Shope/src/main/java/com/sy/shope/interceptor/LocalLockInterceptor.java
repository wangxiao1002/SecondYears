package com.sy.shope.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.sy.shope.annation.LocalLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 幂等锁
 * @author wangxiao
 * @since 1.1
 */
@Aspect
@Component
public class LocalLockInterceptor {
    private static final Cache<String, String> CACHES = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    @Around("@annotation(com.sy.shope.annation.LocalLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object result = joinPoint.proceed();
        if (method.isAnnotationPresent(LocalLock.class)) {
            LocalLock localLock = method.getAnnotation(LocalLock.class);
            String key = generateKey(localLock.key(),method.getName());
            if (CACHES.getIfPresent(key) != null) {
                throw new RuntimeException("短时间内不能重复提交");
            }
            CACHES.put(key, key);
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("服务器异常");
            } finally {
                CACHES.invalidate(key);
            }
        }

        return result;


    }

    private String generateKey (String key,String method) {
       return null == key && "".equals(key.trim()) ? method: key;
    }


}
