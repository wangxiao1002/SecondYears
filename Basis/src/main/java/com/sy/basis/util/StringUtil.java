package com.sy.basis.util;

import com.sy.basis.cache.CacheManager;

/**
 * //字符串工具类
 * @author wangxiao
 * @since 1.1
 */
public class StringUtil {

    public static final String EMPTY_STRING = "";

    public static boolean isEmpty(String source) {
        return null== source || source.length() == 0;
    }

    public static boolean isNotEmpty(String source) {
        return !(isEmpty(source));
    }

    public static boolean isBlank(String source) {
        return null== source || source.isEmpty();
    }

    public static boolean isNotBlank(String source) {
        return !(isBlank(source));
    }
}
