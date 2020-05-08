package com.sy.basis.log;

/**
 * 日志线程类 实现多线程取数 推送到日志平台
 * @author wangxiao
 * @since 1.1
 */
public class LogThread  implements Runnable{



    @Override
    public void run() {
        LogSendService.sendLog(LogUtil.getINSTANCE().getLog());
    }



}
