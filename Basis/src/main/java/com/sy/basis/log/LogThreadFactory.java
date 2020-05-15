package com.sy.basis.log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
* TODO
*
* @author wangxiao
* @since
*/
class LogThreadFactory implements ThreadFactory {
   private static final AtomicInteger poolNumber = new AtomicInteger(1);
   private final AtomicInteger threadNumber = new AtomicInteger(1);
   private final String namePrefix;

   public LogThreadFactory() {
       namePrefix = "LogThread-" + poolNumber.getAndIncrement() + "-";
   }

   @Override
   public Thread newThread(Runnable r) {
       return new Thread( r, namePrefix + threadNumber.getAndIncrement());
   }
}
