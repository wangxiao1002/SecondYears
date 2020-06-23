package com.sy.basis.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 * @author wangxiao
 * @since 1.1
 */
public final class CollectionUtil {
    
    public static final List<?> EMPTY_LIST = new ArrayList<>(0);

    private CollectionUtil () {}

    public static boolean isEmpty (Collection collection) {

        return null == collection || collection.isEmpty();

    }

    public static boolean isNotEmpty (Collection collection) {
        return ! isEmpty(collection);
    }
}
