package com.sy.basis.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期工具
 * @author wangxiao
 * @since 1.1
 */
public class DateUtil {

    private DateUtil() {}

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);

    /**
     *  格式化日期时间
     * @param dateTime 日期时间
     * @return String
     */
    public static String formatDateTime (LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     *  转换日期时间
     * @param dateTime 日期时间
     * @return String
     */
    public static LocalDateTime parseDateTime (String dateTime) {
        return LocalDateTime.parse(dateTime,DATE_TIME_FORMATTER);
    }

    /**
     *  格式化日期
     * @param localDate 日期
     * @return String
     */
    public static String formatDate (LocalDate localDate) {
        return localDate.toString();
    }

    /**
     *  转换日期
     * @param date 日期
     * @return String
     */
    public static LocalDate parseDate (String date) {
        return LocalDate.parse(date,DATE_FORMATTER);
    }

    public static String toDay () {
        return LocalDate.now().format(Constants.DATE_FORMATTER);
    }

    public static String toDayTime () {
        return LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER);
    }

    public static boolean ifSaturday() {
        LocalDate nowDate = LocalDate.now();
        return DayOfWeek.SATURDAY.equals(nowDate.getDayOfWeek());
    }
    public static boolean ifSunday() {
        LocalDate nowDate = LocalDate.now();
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
        return LocalDate.now().with(TemporalAdjusters.previousOrSame( DayOfWeek.SUNDAY));
    }

    public static String weekBeginStr () {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY))
                .format(DATE_FORMATTER);
    }

    public static String weekEndStr () {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame( DayOfWeek.SUNDAY))
                .format(DATE_FORMATTER);
    }


    public static List<String> weekDate () {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame( DayOfWeek.MONDAY));
        LocalDate sunday = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY ));
        return daysBetween(monday,sunday);
    }

    public static List<String> daysBetween (LocalDate beginDate,LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(beginDate, endDate);
        List<String> weekDates = new ArrayList<String>((int) daysBetween);
        for(int i = 0; i <= daysBetween; i++){
            LocalDate tempDate = beginDate.plusDays(i);
            weekDates.add(tempDate.format(Constants.DATE_FORMATTER));
        }
        return weekDates;
    }






}
