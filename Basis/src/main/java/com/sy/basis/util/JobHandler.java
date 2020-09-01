import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zd.gamecontest.support.Constants;
import com.zd.gamecontest.support.GameContestJob;
import com.zd.gamecontest.support.InitQuestionJob;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * job 处理器
 * @author wang xiao
 * @date Created in 12:32 2020/7/22
 */

@Component
public class JobHandler {


    @Autowired
    @Qualifier(value = "taskScheduler")
    private ThreadPoolTaskScheduler taskExecutor;


    private final ZoneOffset defaultZone = ZoneOffset.of("+8");

    private static final int ONE_SECOND_MILLISECOND = 1000;

    private static final long ONE_DAY_MILLISECOND = 24*60*60*ONE_SECOND_MILLISECOND;

    private static final long ONE_HOUR_MILLISECOND = 60*60*ONE_SECOND_MILLISECOND;

    /**
     * 暂存map 方便对定时的修改 查看
     */
    private static final Map<String,ScheduledFuture<?>> SCHEDULED_MAP = new ConcurrentHashMap<>(16);

    public void addJob (Runnable task,String cron) {
        if (StringUtils.isNotEmpty(cron)) {
            Trigger trigger = triggerContext -> {
                CronTrigger cronTrigger = new CronTrigger(cron);
                return cronTrigger.nextExecutionTime(triggerContext);
            };
            ScheduledFuture<?> future = taskExecutor.schedule(task,trigger);
            SCHEDULED_MAP.put(task.toString(),future);
        }
    }

    /**
     *  每日 每周 往表里 插入一条数据 任务
     * @author wangxiao
     * @date 14:02 2020/7/24
     * @param gameContestJob
     * @return void
     */
    public void addJob (GameContestJob gameContestJob) {
        Trigger trigger = triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger("0 1 0 * * ?");
            return cronTrigger.nextExecutionTime(triggerContext);
        };
        ScheduledFuture<?> future = taskExecutor.schedule(gameContestJob,trigger);
        SCHEDULED_MAP.put(gameContestJob.getGameContest().getId(),future);
    }


    /**
     *  每日  开始抽题
     * @author wangxiao
     * @date 14:02 2020/7/24
     * @param dailyJob
     * @return void
     */
    public void addJob (InitQuestionJob dailyJob) {
        Trigger trigger = triggerContext -> {
            CronTrigger cronTrigger = new CronTrigger("0 30 0 * * ?");
            return cronTrigger.nextExecutionTime(triggerContext);
        };
        ScheduledFuture<?> future = taskExecutor.schedule(dailyJob,trigger);
        SCHEDULED_MAP.put("dailyJob",future);
    }



    /**
     * 每x毫秒钟执行
     * @param task 任务
     * @param startSeconds 执行周期（毫秒）
     */
    public void addEveryMillisecond(Runnable task, long startSeconds) {
        taskExecutor.scheduleAtFixedRate(task, startSeconds);
    }

    /**
     * 每x秒钟执行
     * @param task 任务
     * @param startSeconds 执行周期（秒）
     */
    public void addEverySecond(Runnable task, int startSeconds) {
        int millSeconds = startSeconds * ONE_SECOND_MILLISECOND;
        Date startDate = getDelayDate(millSeconds, TimeUnit.MILLISECONDS);
        taskExecutor.scheduleAtFixedRate(task, startDate, millSeconds);
    }

    /**
     * 每x秒钟执行
     * @param task 任务
     * @param startSeconds 延时启动（秒）
     * @param intervalSeconds 执行周期（秒）
     */
    public void addEverySecond(Runnable task, int startSeconds, int intervalSeconds) {
        int millSeconds = intervalSeconds * ONE_SECOND_MILLISECOND;
        Date startDate = getDelayDate(startSeconds * ONE_SECOND_MILLISECOND, TimeUnit.MILLISECONDS);
        taskExecutor.scheduleAtFixedRate(task, startDate, millSeconds);
    }

    /**
     * 每x分钟执行
     * @param task			runnable对象
     * @param startMinute	执行周期时间(分钟)
     */
    public void addEveryMinute(Runnable task, int startMinute) {
        int millSeconds = startMinute * 60 * ONE_SECOND_MILLISECOND;
        Date startDate = getDelayDate(millSeconds, TimeUnit.MILLISECONDS);
        taskExecutor.scheduleAtFixedRate(task, startDate, millSeconds);
    }

    /**
     * 每x分钟执行（整数倍时间）
     * @param task			runnable对象
     * @param startMinute	执行周期时间(分钟)
     */
    public void addEveryMinuteZeroStart(Runnable task, int startMinute) {
        int millSeconds = startMinute * 60 * ONE_SECOND_MILLISECOND;
        Date startDate = getDelayMinuteDate(startMinute);
        taskExecutor.scheduleAtFixedRate(task, startDate, millSeconds);
    }

    /**
     * 每小时整点触发(每天24次） 重复执行
     * @param task	任务
     */
    public void addEveryHour(Runnable task) {
        taskExecutor.scheduleAtFixedRate(task, getNextHourDate(), ONE_HOUR_MILLISECOND);
    }

    /**
     *
     * 每天x点执行.(每天一次) （如果时间已过立即执行一次），然后延迟一天， 重复执行
     * @param task
     * @param hour  1-24 小时定时执行
     */
    public void addFixedTime(Runnable task, int hour) {
        if (hour == 0) {
            hour = 24;
        }
        addFixedTime(task, hour, 0, 0);
    }

    /**
     * 每天x点执行.(每天一次) （如果时间已过立即执行一次），然后延迟一天， 重复执行
     * @param task
     * @param hour
     * @param minutes
     * @param seconds
     */
    public void addFixedTime(Runnable task, int hour, int minutes, int seconds) {
        if (hour == 0) {
            hour = 24;
        }
        LocalTime time = LocalTime.of(hour,minutes,seconds,0);
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(),time);
        if (localDateTime.toEpochSecond(defaultZone)< System.currentTimeMillis()) {
            localDateTime = localDateTime.plusDays(1);
        }
        taskExecutor.scheduleAtFixedRate(task, Date.from(localDateTime.toInstant(defaultZone)), ONE_DAY_MILLISECOND);
    }

    /**
     * 延迟执行
     * @param task 任务
     * @param seconds 延迟时间(秒)
     */
    public void addDelaySeconds(Runnable task, int seconds) {
        long millSeconds = TimeUnit.SECONDS.toMillis(seconds);
        taskExecutor.schedule(task, new Date(System.currentTimeMillis() + millSeconds));
    }



    private Date getDelayDate (int period,TimeUnit timeUnit) {
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        localTime =localTime.plus(period, ChronoUnit.SECONDS);
        LocalDateTime nowDateTime = LocalDateTime.of(localDate, localTime);
        return Date.from(nowDateTime.toInstant(defaultZone));
    }

    private Date getDelayMinuteDate (long period) {
        LocalDateTime localDateTime = LocalDateTime.now(defaultZone);
        localDateTime = localDateTime.plusMinutes(period);
        return Date.from(localDateTime.toInstant(defaultZone));
    }


    private Date getNextHourDate () {
        LocalDateTime localDateTime = LocalDateTime.now(defaultZone);
        localDateTime = localDateTime.plusHours(1);
        return Date.from(localDateTime.toInstant(defaultZone));
    }

    public static Map<String, ScheduledFuture<?>> getScheduledMap() {
        return SCHEDULED_MAP;
    }
}
