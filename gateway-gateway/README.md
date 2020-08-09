# GateWay
## 区别与zuul
* zuul基于Servlet io 实现拦截请求（zuul2.0 不同）,GateWay使用Reactor模式 webFlux 实现，底层使用的是Netty
* Servlet io 和webFlux 区别，传统的Servlet 是由servlet container进行生命周期的管理，容器负责Servlet的Init和destory，
当接受一个请求时候就会分配一个线程（从线程池中拿）调用Service（）</br>
WebFlux 使用少量的线程去处理Request 和Response Io,而业务逻辑交给响应式框架去处理，用户将业务阻塞工作交给Work线程去处理
## GateWay处理流程
客户端向 Spring Cloud Gateway 发出请求。然后在 Gateway Handler Mapping 中找到与请求相匹配的路由，将其发送到 Gateway Web Handler。
Handler 再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑，然后返回。
## GateWay 路由匹配规则
* gateWay使用的是断言（Predicate）
* 断言实现了一组匹配规则，可以根据（dateTime请求时间、cookie 、Header、host、method、Path、Query、RemoteAddr）做匹配
### path 匹配
```yaml
  - id: oschina # 路由的编号
   uri: https://www.oschina.net # 路由的目标地址
   predicates: # 断言，作为路由的匹配条件，对应 RouteDefinition 数组
     - Path=/oschina
     - Path=/foo/{segment}
```
### queryParam匹配
*  按照query 参数名称匹配（有该参数则匹配没有则不匹配路由）
```yaml
 # 当请求参数中只有 testQuery参数的时候才会匹配
 -id: gateway-service
​  uri: https://www.baidu.com
​  order: 0
​  predicates:
​     -Query=testQuery
```
* 按照请求参数的键值对匹配
```yaml
  # 属性值为keep 且值为pu 时候才会匹配
  -id: gateway-service
​   uri: https://www.baidu.com
​   order: 0
​   predicates:
      -Query=keep, pu.
```
* 按照Header 去做匹配，Header Route Predicate 接受俩个参数，第一个是名称，第二个是正则表达式
```yaml
    # 只有名字满足匹配正则时候才会匹配路由
  -id: gateway-service
​   uri: https://www.baidu.com
​   order: 0
​   predicates:
​     - Header=X-Request-Id, \d+
```
* 按照cookie 去匹配路由
```yaml
  # 当cookie 的值为sessionId value是test 匹配 当然第二个参数正则
​  -id: gateway-service
​   uri: https://www.baidu.com
​   order: 0
​   predicates:
​     - Cookie=sessionId, test
```
* Host匹配路由
```yaml
    # 当请求的host 为*.baidu.com满足，比如md.baidu.com
​   -id: gateway-service
​    uri: https://www.baidu.com
​    order: 0
​    predicates:
​      - Host=**.baidu.com
```
* 通过请求的Method 来匹配路由
```yaml
    # 匹配Get 请求
​    -id: gateway-service
     uri: https://www.baidu.com
     order: 0
     predicates:
       - Method=GET
```
* 通过ip 匹配
```yaml


​  - id: gateway-service
    uri: https://www.baidu.com
    order: 0
    predicates:
     - RemoteAddr=192.168.1.1/24
```
## 服务的熔断 
```yaml
 - id: rateLimit_route
          uri: http://localhost:8000
          order: 0
          predicates:
            - Path=/test/**
          filters:
            - StripPrefix=1 作用是去掉请求路径的最前面n个部分截取掉
            - name: Hystrix
              args:
                name: fallbackCmdA
                fallbackUri: forward:/fallbackA 自定义熔断返回json

  hystrix.command.fallbackCmdA.execution.isolation.thread.timeoutInMilliseconds: 5000
```
## 限流
```yaml
  # 令牌桶算法 redis 限流配置
spring:
  cloud:
    gateway:
      routes:
      - id: limit_route
        uri: http://httpbin.org:80/get
        predicates:
        - After=2017-01-20T17:42:47.789-07:00[America/Denver]
        filters:
        - name: RequestRateLimiter
          args:
            key-resolver: '#{@userKeyResolver}' # key-resolver，用于限流的键的解析器的 Bean 对象的名字。它使用 SpEL 表达式根据#{@beanName}从 Spring 容器中获取 Bean 对象。
            redis-rate-limiter.replenishRate: 1 令牌每秒填充速度
            redis-rate-limiter.burstCapacity: 3 令牌容量
  application:
    name: cloud-gateway
  redis:
    host: localhost
    port: 6379
    database: 0
```
定义userKeyResolver Bean
```java
// 根据userId限流 
@Bean
public KeyResolver userKeyResolver() {
  return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
}
// 通过ip
@Bean
public KeyResolver ipKeyResolver() {
  return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
}
```
