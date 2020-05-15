package com.sy.basis.log;

import java.util.concurrent.TimeUnit;

/**
 * 日志线程类 实现多线程取数 推送到日志平台
 * @author wangxiao
 * @since 1.1
 */
public class LogThread  implements Runnable{



    @Override
    public void run() {
        while (true) {
            if (LogUtil.getINSTANCE().isEmpty()) {
                LogSendService.sendLog(LogUtil.getINSTANCE().getLog());
            }else {
                try {
                    TimeUnit.SECONDS.sleep(15L);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

    }



}
