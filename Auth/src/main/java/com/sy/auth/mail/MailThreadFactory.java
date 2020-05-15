package com.sy.auth.mail;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 邮件线程池
 * @author wangxiao
 * @since 1.1
 */
public class MailThreadFactory  implements  ThreadFactory{

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public MailThreadFactory() {
        this.namePrefix = "MailThread-" + poolNumber.getAndIncrement() + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, this.namePrefix + this.threadNumber.getAndIncrement());
    }
}
