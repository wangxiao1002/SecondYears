package com.sy.basis.log;

/**
 * 实现日志控制工具类
 * @author wangxiao
 * @since
 */
public class LogUtil {

    private static LogUtil INSTANCE = new LogUtil();
    private LogUtil () {}

    public static LogUtil getINSTANCE() {
        return INSTANCE;
    }

     /**
      * 添加日志
      * @author wangxiao
      * @param logEntity 日志
      * @return 是否成功
      **/
    public boolean addLog(LogEntity logEntity) {
        return LogManager.push(logEntity);
    }

     /**
      * 获取日志
      * @author wangxiao
      * @return LogEntity
      **/
    protected LogEntity getLog() {
        return LogManager.pop();
    }
}
