package com.sy.basis.util;



/**
 * //字符串工具类
 * @author wangxiao
 * @since 1.1
 */
public final class StringUtil {

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

    public static boolean isAsciiAlphanumeric(String  source) { {
            if (source == null) {
                return false;
            }
            int sz = source.length();
            for (int i = 0; i < sz; i++) {
                if ((!Character.isLetterOrDigit(source.charAt(i))) && (source.charAt(i) != ' ')) {
                    return false;
                }
            }
            return true;
        }

    }

    public static boolean isAsciiAlphanumeric(char  source) { {
        return (!Character.isLetterOrDigit(source)) && (source != ' ');
    }

    }
    
}
