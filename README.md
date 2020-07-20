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
3. Notify 消息系统(私信、公告) 和聊天（群聊）系统,过滤敏感词（H2存储),微信公众号验证伪(模板消息和客服消息)
4.  Buniess 业务系统 视频流转换
5. sy-web 前端界面
## 发布构建
**每个服务都提供Docker Image可快速启动**
1. 首先部署注册中心，SpringBoot 正常启动
2. 部署配置中心，将git密码修改自己的，具体配置在讲解在wiki:[项目配置里面](https://github.com/wangxiao1002/SecondYears/wiki/%E9%A1%B9%E7%9B%AE%E9%9C%80%E8%A6%81%E9%85%8D%E7%BD%AE%E5%8F%82%E6%95%B0)
3. 启动单个服务
4. 启动前端 npm run dev
## 演示
体验地址(个人服务器较小，暂无)

1. EurakeServer 注册中心

   ![EurakeServer](https://note.youdao.com/yws/api/personal/file/E97E19BE2E0E427CB08A44A0D3148BB3?method=download&shareKey=4cac55722846dc33bb9e591f1c25a50c)

2.  monitor 监视服务

   ![SpringAdmin](https://note.youdao.com/yws/api/personal/file/89CD7CB2E1CE4AA08385A229AFE9513D?method=download&shareKey=96580ab6bc52791f2a6ca54b9d0ad496)

3. DockerFile

   ```dockerfile
   # Docker image for config-servers
   # VERSION 0.0.1
   # Author: wangxiao
   # 基础镜像使用java
   FROM java:8
   # 将jar包添加到容器中并更名为app.jar
   ADD auth-0.0.1-SNAPSHOT.jar auth.jar 
   # 运行jar包
   #RUN bash -c 'touch /auth.jar'
   CMD java -jar auth.jar 
   
   # ENTRYPOINT ["java","-Dspring.config.profile=${profile}","-jar","/eurake-server.jar"]
   ```
4. nginx.conf 
 * 普通的conf不必多配置简单的负载均衡，主要使用Nginx给商城配置限流
 * 在http 模块申明限流 (binary_remote_addr 二进制客户端地址，zone=mylimit (申明的限流空间mylimit 默认1M) rate 限流秒级100)
 ```
 limit_req_zone $binary_remote_addr zone=mylimit:10m rate=100r/s
 ```
 * Server模块使用 (默认使用露桶算法 低于20 快速转发)
 ```
   server {
         location /test/ {
             limit_req zone=mylimit burst=20 nodelay;
    
             proxy_passhttp://backend;
         }
     }
 ```

## 项目进度和实现功能
1. 基础服务（eurake config zuul） 构建 done
3. 自定义缓存，日志异步收集、异步发邮件接口 done
2. auth 认证功能实现，提供菜单、权限点配置授权和crud功能 done
3. Notify/ 消息模块构建 实现单聊、群聊(done)和消息推送提醒功能 done
4. sy-web 前端界面 doing -> cancle (服务器到期)
5. Shope 商城 sku,spu 计算价格 done 微信支付done es搜索 done 使用百度查询快递(百度不支持顺丰 正在研究so.com) done
7. Shope 实现秒杀功能 
## 直播（实现重点）
1. 之前认为的直播是 后端推送二进制流，类似webSocket推送，结合netty 自定义协议，前端再去解析协议(错误的认知)
2. obs:[obs推流](https://github.com/obsproject/obs-studio) 
3. nginx-http-flv-module[](https://github.com/winshining/nginx-http-flv-module)
4. 推流示例图片 ![示例图片](https://github.com/winshining/nginx-http-flv-module/blob/master/samples/flv.js.png)
5. 整合Obs和flv module 两部分就可以实现直播，直播地址是flv流地址，以斗鱼为例：https://tc-tct.douyucdn2.cn/dyliveflv1a/606118r2gsAFhJK6_4000.flv?wsAuth=3120b69d0c6156a6322d5a1103c97498&token=web-h5-113948271-606118-58f5e9796237bc95d7b2b35fc16003e84790c61059478871&logo=0&expire=0&did=d75c4f240767caf64eddd3b400091501&ver=Douyu_220062405&pt=2&st=0&origin=all&mix=0&isp=
6. flv 地址的有效性,当一个推流地址一直有效别人就可以随意观看，所以需要每隔一段时间在网页端切换推流地址
## 项目构建部署
1. Cloud Toolkit
2. docker
## 使用SpringCloud组件
1. eurake Server 

2. config Server

3. spring admin

## 结语
原本想把项目做成直播系统，但最近由于众多原因（资金和时间）,其实更重要的是因为个人水平不够，挑战一些源码时候看不懂，所以决定学习一波源码，重构基础。搁浅直播
