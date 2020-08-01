package com.sy.nettyrpc;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * 扫描代理工厂类
 * @author wangxiao
 * @since 1.1
 */
public class NettyRpcClientFactoryBean implements FactoryBean<Object> {


    private Class<?> type;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(type.getClassLoader(),new Class[] {type},new NettyRpcInvocationHandler(type));
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
