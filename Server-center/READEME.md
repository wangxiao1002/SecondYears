# 服务中心-Eureka 双节点集群
**主要解决以下问题:**
1. 服务注册
2. 服务间调用
3. 注册中心的高可用
## 服务注册 
* 客户端启动会注册自己
* 客户端定期向服务端发心跳 确保自己是活跃状态
* 客户端定期会定期检索服务端服务列表
* 客户端会根据自己的服务列表来请求另一个客户端
## 注意参数
###服务端
1. fetch-registry: 开启检索，单个实例不开启
2. enable-self-preservation: 自我保护机制，开启
3. eviction-interval-timer-in-ms: 扫描失效服务时间
4. wait-time-in-ms-when-sync-empty: 0 设置eureka server同步失败的等待时间
### 客户端
 
 1. registry-fetch-interval-seconds:  从eureka 上获取服务列表时间 30
 
 2. instance-info-replication-interval-seconds: 复制实例变化信息到eureka服务器所需要的时间间隔
 3. lease-renewal-interval-in-seconds Eureka服务器在接收到本实例的最后一次发出的心跳后，需要等待多久才可以将此实例删除
 4. lease-expiration-duration-in-seconds:   eureka客户需要多长时间发送心跳给eureka服务器
       