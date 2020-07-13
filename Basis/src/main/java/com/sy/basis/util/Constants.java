package com.sy.basis.util;

import java.time.format.DateTimeFormatter;

/**
 * @author: wang xiao
 * @description:
 * @date: Created in 15:40 2020/7/2
 */
public final class Constants {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String  ERROR_CODE = "0";

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    public final static String STATUS ="status";

}
