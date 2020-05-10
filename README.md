# SecondYears

2020年是工作的将近第二年，回想了之前忙碌的一年半，做微服务 知其所以然，但不知其所以然。

自己的博客也没有精力去维护，所以想在github上面维护一个自己阅读源码笔记和微服务实践过程中遇到的问题，以及看到别人优秀的代码，用来学习</br>
当然最重要的是完成当时一个想法：**直播平台** </br>
## 直播平台主要功能模块
1. 认证登录，匿名登录微信登录，
2. 做权限判断 网关
3. IM 系统，点对点单聊和 群聊
4. 最重要的视屏直播推流
5. 文件存储，直播录像视频
6. 礼物商城
7. 待完善

 **准备工作---start**
## 分布式ID策略
实现snowflake 算法，主要生成菜单、按钮、权限点等code
## Eurake Server 集群
服务注册中心有很多，做微服务项目以来，从开始的Zk,到使用阿里nacos,还有eurake。
使用@EnableDiscoveryClient 都可以将服务注册到注册中心上去，是因为SpringCloud 提供良好的抽象配置，DiscoveryClient bean 均可获取服务信息。</br>
这里搭建Eurake Server 集群是因为之前的技术leader告诫过用Eurake 使用集群方式更好，单个Eurake远远不如nacos,Eurake集群方式思想就是你中有我、我中有你。
Eurake server1向注册EurakeServer2 注册，2则反之。</br>
服务查看地址/server-center；注册地址：/eurake

## 网关 zuul filter
使用Zuul做网关是我第一次接触微服务就是用的架构服务，据说现在好多都抛弃Zuul 可能是因为zuul是通过Servlet来实现</br>
在zuul网关中做了统一认证、鉴权、异常处理。
鉴权思想:用户关联角色，，角色控制菜单渲染、角色控制请求地址，请求地址是权限点拦截用户请求地址 method@uri与数据库配置的这个地址匹配(当然数据配置是方法名@地址,</br>
eg:GET*/auth/user/login)</br>
匹配工具使用AntPathMather使用规则如下:</br>
可以做URLs匹配，规则如下

1. ?匹配一个字符
2. *匹配0个或多个字符
3. **匹配0个或多个目录 </br>
使用eg:pathMatcher.match(patternUrl,uri)
后期优化使用SpringCache 对权限点做缓存</br>


##  自定义缓存模块
redis在面试还是日常中经常用来做缓存，为了更加理解redis,自己动手实现缓存功能
### 缓存数据结构选择
使用ConcurrentHashMap,HashMap,HashTable都可以存储数据,区别是线程安全和是否允许为空。为实现简易化key不允许为空和保证并发先择ConcurrentHashMap作为底层的数据结构。
### 缓存删除策略
1. 定时删除。
   在设置key,value,expireTime时候根据expireTime设置定时任务，到期删除
2. 惰性删除
   获取值时候判断是否过期
3. 定期删除
  定期去轮询超时时间判断是否过期
 ### 持久化待更新
 
 ## 日志收集
 日志统计使用异步方式，将日志收集到阻塞队列ArrayBlockingQueue中，再利用多线程将日志发送到日志平台
 ## 配置中心
 添加配置中心统一管理配置,配置中心配置账号密码
  ## auth  
 1. 负责用户认证登录 授权
 2. 使用springCache 缓存权限点查询，单个缓存时间是5分钟
 3. 提供后台管理接口
 4. 对接任意登录接口，目前支持手机快速登录、微信扫码+手机号登录(遗留待短信平台和微信开发者)
 ## monitor 监视器
1. 使用spring admin 搭建服务监控系统，确保内存等信息被监控到
2. 遗留问题 因每一个服务都配置了context-path 导致健康检查等路劲访问不到 admin 需要配置元数据

 **准备工作---end**
# 业务模块开发
1. 业务模块主要有四大部分： IM,直播（视频）推流，商城，前端
2. IM (私聊，群聊)以及包含消息通知（邮件，web消息）
3. 视频流（暂无方案）
4. 商城（商品的CRUD,商品图标，自定义商品，礼物抽奖）
5. 前端 web界面是用vue+iview

 
 
