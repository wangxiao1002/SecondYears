package com.sy.basis.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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



}
