package com.sy.basis.util;

import java.util.Collection;

/**
 * 集合工具类
 * @author wangxiao
 * @since 1.1
 */
public final class CollectionUtil {

    private CollectionUtil () {}

    public static boolean isEmpty (Collection collection) {

        return null == collection || collection.isEmpty();

    }

    public static boolean isNotEmpty (Collection collection) {
        return ! isEmpty(collection);
    }
}
