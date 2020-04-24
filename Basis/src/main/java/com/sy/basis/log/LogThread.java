package com.sy.basis.log;

/**
 * 日志线程类 实现多线程取数 推送到日志平台
 * @author wangxiao
 * @since 1.1
 */
public class LogThread  implements Runnable{

    private LogSendService logSendService;

    @Override
    public void run() {
        logSendService.sendLog(LogUtil.getINSTANCE().getLog());
    }

    public LogThread(LogSendService logSendService) {
        this.logSendService = logSendService;
    }

}
