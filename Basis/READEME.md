# 基础类
**主要解决以下问题:**
1. 共用实体类不用重复创建
2. 共用方法不能项目之间共享
3. 提供基础功能，自定义缓存、日志收集等
4. 分布式id 生成，
## 自定义本地缓存模块
1. CacheEntity 缓存实体类
2. CacheManager 缓存管理器，用来存放缓存,启动清理线程
3. CacheUtil 提供缓存工具类
## 日志收集
使用阻塞队列来收集日志，线程池来发送日志 遗留问题(此包中不应该用多线程触发，只应该提供示例 )</br>
具体实现如下：
```
    //  继承 LogSendService 实现自己发送方法，再利用线程池触发，但只是示例
    static {
        final ExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.execute(new LogThread(new LogSendService()));
        executorService.execute(new LogThread(new LogSendService()));
        executorService.execute(new LogThread(new LogSendService()));
    }
```
## 分布式ID生成
* snowflake 算法
* 使用机器号和进程号做 workeid、dataCennterId 保证不同机器上不重复
**重要类有:**
1. LogEntity 日志实体类
2. LogUtil 日志工具，提供添加日志
3. LogThread 日志线程
4. LogManager 日志管理日 包含阻塞队列操作
5. LogSendService 日志发送service 具体业务需要自己实现，并通过LogThread构造参数传递进入

