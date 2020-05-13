# SecondYears

2020年是我工作的将近满第二年，回想了之前忙碌的一年半，做微服务、容器化 知其所以然，但不知其所以然。
自己的博客也没有精力去维护，所以想在github上面维护一个项目，完成自己最初的想法：**直播平台** </br>
## 项目模块
**每个模块功能介绍在wiki：[项目模块功能实现](https://github.com/wangxiao1002/SecondYears/wiki/%E9%A1%B9%E7%9B%AE%E6%A8%A1%E5%9D%97%E5%85%B7%E4%BD%93%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D)**
### 基础模块
1. Eurake Server (server-center)集群，担任整个注册中心 
2. 网关 zuul filter(gateway-zuul) 认证鉴权加转发后期使用kong准备代替
3. 配置中心Spring Config Server(config-center) 配置中心
4. 基础服务（basis） 提供Snowflake分布式id算法、基础工具类、自定义缓存、日志收集等功能
### 功能模块
1. Auth 管理中心，用户认证、邮件服务、消息通知等以及后台管理功能
2. Shope 商城 提供购买礼物，抽奖、定制化礼物等功能
3. SYIM 通讯模块，提供单聊和群聊方式聊天，和通告信息
4. sy-web 前端界面
## 发布构建
**每个服务都提供Docker Image可快速启动**
1. 首先部署注册中心，SpringBoot 正常启动
2. 部署配置中心，将git密码修改自己的，具体配置在讲解在wiki:[项目配置里面](https://github.com/wangxiao1002/SecondYears/wiki/%E9%A1%B9%E7%9B%AE%E9%9C%80%E8%A6%81%E9%85%8D%E7%BD%AE%E5%8F%82%E6%95%B0)
3. 启动单个服务
4. 启动前端 npm run serve
 
 
