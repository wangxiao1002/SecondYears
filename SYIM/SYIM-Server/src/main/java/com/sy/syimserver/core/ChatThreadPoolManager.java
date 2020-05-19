package com.sy.syimserver.core;

import com.sy.basis.util.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 聊天线程池
 * @author wangxiao
 * @since 1.1
 */
public class ChatThreadPoolManager {

    private static  ThreadFactory chatThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-chat-%d").build();


    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(20,40,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(),chatThreadFactory);


    public static void submit (FutureTask<String> futureTask) {
         executor.submit(futureTask);
    }
}
