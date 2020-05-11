# Monitor 监视器
## 主要功能
1. spring admin 的功能
2. 内存监控、
3. 为安全起见配置密码
## 因为配置context-path 健康检查问题
配置了context-path 但是在eurake 注册中心上，服务还是按照未配置context-path地址做健康检查，导致admin 监控到的服务全部是DOWN</br>
**解决方案**
</br>
1. 配置元数据
```
eureka:
  instance:
    metadata-map:
      management:
        context-path: ${server.servlet.context-path}/actuator

```
2. 配置健康检查地址
```
eureka:
  instance:
    health-check-url-path: ${server.servlet.context-path}/actuator/health
    status-page-url-path: ${server.servlet.context-path}/actuator/info

```
