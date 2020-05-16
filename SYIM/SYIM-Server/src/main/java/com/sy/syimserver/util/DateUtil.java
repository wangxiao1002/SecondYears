package com.sy.syimserver.util;

import sun.util.resources.LocaleData;

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

    private DateUtil () {}

    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA);


    public static String formatDateTime (LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseDateTime (String dateTime) {
        return LocalDateTime.parse(dateTime,DATE_TIME_FORMATTER);
    }

    public static String formatDate (LocalDate localDate) {
        return localDate.toString();
    }
    public static LocalDate parseDate (String date) {
        return LocalDate.parse(date,DATE_FORMATTER);
    }



}
