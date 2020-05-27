package com.sy.shope.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.sy.shope.annation.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 实现拦截
 * @author wangxiao
 * @since 1.1
 */
@Aspect
@Component
public class LimitInterceptor {


    private final RateLimiter rateLimiter = RateLimiter.create(100);

    @Around("@annotation(com.sy.shope.annation.RateLimit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(RateLimit.class)) {
            RateLimit rl = method.getAnnotation(RateLimit.class);
            rateLimiter.setRate(rl.perSecond());
            if (!rateLimiter.tryAcquire(rl.timeOut(), rl.timeOutUnit())) {
                throw new RuntimeException("访问人数太多,请稍后再试试");
            }
        }
        return pjp.proceed();
    }
}
