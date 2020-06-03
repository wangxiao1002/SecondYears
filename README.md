# SecondYears

2020年是我工作的将近满第二年，回想了之前忙碌的一年半，做微服务、容器化 知其所以然，但不知其所以然。
自己的博客也没有精力去维护，所以想在github上面维护一个项目，完成自己最初的想法：**直播平台** </br>
## 项目模块
**每个模块功能介绍在wiki：[项目模块功能实现](https://github.com/wangxiao1002/SecondYears/wiki/%E9%A1%B9%E7%9B%AE%E6%A8%A1%E5%9D%97%E5%85%B7%E4%BD%93%E5%8A%9F%E8%83%BD%E4%BB%8B%E7%BB%8D)**
### 基础模块
1. Eurake Server (server-center)集群，担任整个注册中心 
2. 网关 zuul filter(gateway-zuul) 认证鉴权加转发后期使用kong准备代替
3. 配置中心Spring Config Server(config-center) 配置中心
4. 基础服务（basis） 提供Snowflake分布式id算法、基础工具类、自定义LRU缓存、日志收集等功能
5. 监控服务Spring admin（monitor）监控服务状态、内存信息等
### 功能模块
1. Auth 管理中心，用户认证、邮件服务及后台管理功能
2. Shope 商城 提供购买礼物，抽奖、定制化礼物等功能
3. Notify 消息系统(私信、公告) 和聊天（群聊）系统，
4.  Buniess 业务系统 视频流转换
5. sy-web 前端界面
## 发布构建
**每个服务都提供Docker Image可快速启动**
1. 首先部署注册中心，SpringBoot 正常启动
2. 部署配置中心，将git密码修改自己的，具体配置在讲解在wiki:[项目配置里面](https://github.com/wangxiao1002/SecondYears/wiki/%E9%A1%B9%E7%9B%AE%E9%9C%80%E8%A6%81%E9%85%8D%E7%BD%AE%E5%8F%82%E6%95%B0)
3. 启动单个服务
4. 启动前端 npm run serve
## 演示
体验地址
## 项目进度和实现功能
1. 基础服务（eurake config zuul） 构建 done
3. 自定义缓存，日志异步收集、异步发邮件接口 done
2. auth 认证功能实现，提供菜单、权限点配置授权和crud功能 done
3. Notify/ 消息模块构建 实现单聊、群聊(done)和消息推送提醒功能 doing
4. sy-web doing
5. Shope 商城 doing



 
