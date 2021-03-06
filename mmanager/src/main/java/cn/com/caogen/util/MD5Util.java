package cn.com.caogen.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * author:huyanqing
 * Date:2018/4/11
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++){
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 'h');
        }
        String s = new String(a);
        return s;

    }


    @Test
    public void test() {

            System.out.println(string2MD5("wangzongyi.123"));
        System.out.println(convertMD5(convertMD5("857089949d71ed6506519af58d1616ce")));

    }
}
