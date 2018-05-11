package cn.com.caogen.weixin.util;

/**
 * author:huyanqing
 * Date:2018/5/8
 */

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * <p>Title: SHA1算法</p>
 *
 * @author levi
 */
public final class SHA1 {

   /* private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < len; i++) {
            shaHex = Integer.toHexString(bytes[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }



    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/


    public static String getSHA1(String token, String timestamp, String nonce)
    {
        try {
            String[] array = new String[] { token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";

        }

    }



}

