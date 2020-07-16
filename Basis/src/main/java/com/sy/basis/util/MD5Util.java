package com.sy.basis.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * md5
 * @author wangxiao
 * @since 1.1
 */
public class MD5Util {

    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        byte[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            byte item = var4[var6];
            sb.append(Integer.toHexString(item & 255 | 256).substring(1, 3));
        }

        return sb.toString().toUpperCase();
    }
}
