# SecondYears

2020年是工作的将近第二年，回想了之前忙碌的一年半，做微服务 知其所以然，但不知其所以然。

自己的博客也没有精力去维护，所以想在github上面维护一个自己阅读源码笔记和微服务实践过程中遇到的问题，以及看到别人优秀的代码，用来学习</br>
## Eurake Server 集群
服务注册中心有很多，做微服务项目以来，从开始的Zk,到使用阿里nacos,还有eurake。
使用@EnableDiscoveryClient 都可以将服务注册到注册中心上去，是因为SpringCloud 提供良好的抽象配置，DiscoveryClient bean 均可获取服务信息。</br>
这里搭建Eurake Server 集群是因为之前的技术leader告诫过用Eurake 使用集群方式更好，单个Eurake远远不如nacos,Eurake集群方式思想就是你中有我、我中有你。

## 网关 zuul filter
使用Zuul 做网关是我第一次接触网关服务，据说现在好多都抛弃Zuul 因为实现是通过Servlet，再做百度noah网关时候做了统一认证、鉴权。
鉴权思想 角色控制菜单渲染、角色控制请求地址，拦截用户请求地址 uri + method 然后做匹配。后期优化使用SpringCache 对权限点做缓存</br>
