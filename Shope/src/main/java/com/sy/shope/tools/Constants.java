package com.sy.shope.tools;

import java.math.BigDecimal;
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

    public static final String FAIL_XML = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[fail]]></return_msg></xml> ";

    public static final String SUCCESS_XML = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";

    public static final BigDecimal HUNDRED = BigDecimal.TEN.multiply(BigDecimal.TEN);

}
