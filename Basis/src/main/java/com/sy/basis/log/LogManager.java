package com.sy.basis.log;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 日志管理
 * @author wangxiao
 * @since 1.1
 */
public class LogManager {

    private static LogManager MANAGER = new LogManager();



    private static ArrayBlockingQueue<LogEntity> LOG_QUEUE = new ArrayBlockingQueue<>(500);

    private LogManager () {}

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,5,10,TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10),new LogThreadFactory(),new ThreadPoolExecutor.DiscardPolicy());
        executor.execute(new LogThread());
        executor.execute(new LogThread());
        executor.execute(new LogThread());
    }

     /**
      * 存数据
      * @author wangxiao
      * @param logEntity 日志实体
      **/
    protected static boolean push(LogEntity logEntity) {
        return LOG_QUEUE.offer(logEntity);
    }

     /**
      * 取数据
      * @author wangxiao
      **/
     protected static LogEntity pop() {
        return LOG_QUEUE.poll();
    }


    protected static boolean isEmpty () {
         return LOG_QUEUE.isEmpty();
    }


}

