package com.sy.basis.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wang xiao
 * @date Created in 11:26 2020/7/27
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter TIME_FORMATTER  = DateTimeFormatter.ofPattern("HH:mm");

    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");


    /**
     *  下面四个方法放在这里是因为 共用 ZoneId 其实没必要合在一起
     * @author wangxiao
     */
    public static long toEpochMilli (LocalDateTime localDateTime) {
        return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    public static long toEpochMilli (LocalDate localDate) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.now());
        return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    public static long toEpochMilli (LocalDate localDate,LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
    }

    public static LocalDate parseMilliToLocalDate (long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZONE_ID).toLocalDate();
    }
    public static LocalDateTime parseMilliToLocalDateTime (long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }



    public static String toDay () {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String toDayTime () {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static boolean ifSaturday(String  dateText) {
        LocalDate nowDate = LocalDate.parse(dateText);
        return DayOfWeek.SATURDAY.equals(nowDate.getDayOfWeek());
    }
    public static boolean ifSunday(String dateText) {
        LocalDate nowDate = LocalDate.parse(dateText);
        return DayOfWeek.SUNDAY.equals(nowDate.getDayOfWeek());
    }

    public static String coverTime(Long secondNumber) {
        if (Objects.isNull(secondNumber)) {
            return "--";
        }
        long minutes = secondNumber / 60;
        long remainingSeconds = secondNumber % 60;
        return String.format("%d分%d秒",minutes,remainingSeconds);
    }

    public static LocalDate weekBegin () {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
    }

    public static LocalDate weekEnd () {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame( DayOfWeek.SUNDAY));
    }

    public static String weekBeginStr () {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY))
                .format(DATE_FORMATTER);
    }

    public static String weekEndStr () {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame( DayOfWeek.SUNDAY))
                .format(DATE_FORMATTER);
    }

    public static String weekBeginStr (String dateText) {
        return LocalDate.parse(dateText).with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY))
                .format(DATE_FORMATTER);
    }

    public static String weekEndStr (String dateText) {
        return LocalDate.parse(dateText).with(TemporalAdjusters.nextOrSame( DayOfWeek.SUNDAY))
                .format(DATE_FORMATTER);
    }


    public static List<String> weekDate () {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));
        return daysBetween(monday,sunday);
    }
    public static List<String> weekDate (String dateText) {
        LocalDate today = LocalDate.parse(dateText);
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));
        return daysBetween(monday,sunday);
    }

    public static List<String> daysBetween (LocalDate beginDate,LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(beginDate, endDate);
        List<String> weekDates = new ArrayList<String>((int) daysBetween);
        for(int i = 0; i <= daysBetween; i++){
            LocalDate tempDate = beginDate.plusDays(i);
            weekDates.add(tempDate.format(DATE_FORMATTER));
        }
        return weekDates;
    }



    public static boolean ifBeforeToDay (String dateStr) {
        LocalDate now =  LocalDate.now();
        LocalDate gameDate = LocalDate.parse(dateStr);
        return gameDate.isEqual(now) || gameDate.isBefore(now);
    }

    public static boolean isAfterToday (String dateStr) {
        LocalDate now =  LocalDate.now();
        LocalDate gameDate = LocalDate.parse(dateStr);
        return  gameDate.isAfter(now);
    }


}
