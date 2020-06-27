package com.sy.shope.tools;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 常量
 * @author wangxiao
 * @since 1.1
 */
public class Constants {
    private Constants () {}

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final List<?> EMPTY_LIST = new ArrayList<>(0);

}
