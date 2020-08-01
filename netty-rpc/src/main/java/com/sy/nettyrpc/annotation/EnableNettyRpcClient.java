package com.sy.nettyrpc.annotation;

/**
 * 启动扫描注解
 * @author wangxiao
 */
public @interface EnableNettyRpcClient {

    String [] basePackages () default {};

    Class<?> [] basePackageClasses () default  {};

}
