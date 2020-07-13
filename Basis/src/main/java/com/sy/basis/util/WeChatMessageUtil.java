package com.sy.basis.util;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 微信公众号 消息工具类
 * @author wangxiao
 * @since 1.1
 */
public class WeChatMessageUtil {



    /**
     * 校验签名
     * @param signature 微信加密签名.
     * @param timestamp 时间戳.
     * @param nonce 随机数.
     * @return
     */
    public static boolean checkSignature(String token,String signature, String timestamp, String nonce) {
        String[] paramArr = new String[] {token, timestamp, nonce};
        Arrays.sort(paramArr);

        String content  = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ciphertext != null && ciphertext.equals(signature.toUpperCase());
    }

    /**
     * add by  wangxiao
     * @date 14:20 2020/7/10
     * @param byteArray
     * @return java.lang.String
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * add by  wangxiao
     * @date 14:20 2020/7/10
     * @param mByte
     * @return java.lang.String
     */
    private static String byteToHexStr(byte mByte) {
        final char[] digit = { '0', '1' , '2', '3',
                '4' , '5', '6', '7' , '8',
                '9', 'A' , 'B', 'C', 'D' , 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        return new String(tempArr);
    }


}
