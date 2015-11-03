package com.lv.tools;

/**
 * Created by lv on 15-7-28.
 */
public class ConvertUtil {
    /**
     * byte数组转字符串
     *
     * @param bytein
     * @param addSpace 是否需要添加空格
     * @return
     */
    public static String byte2HexString(byte[] bytein, boolean addSpace) {
        String string = "";
        for (int i = 0; i < bytein.length; i++) {
            try {
                String rule = addSpace ? "%02X " : "%02X";
                string += String.format(rule, bytein[i]);
            } catch (Exception e) {
                break;
            }
        }
        return string;
    }

    /**
     * byte数组转字符串
     *
     * @param bytein
     * @return
     */
    public static String byte2HexString(byte[] bytein) {
        return byte2HexString(bytein, true);
    }

    /**
     * 截取0-8的有小身份证ＩＤ
     *
     * @return
     */
    public static byte[] subString8(byte[] bytes) {
        byte[] newBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            newBytes[i] = bytes[i];
        }
        return newBytes;
    }

    /**
     * 截图8个有效的身份证ID
     *
     * @param bytes 从流中读到的byte
     * @return　有效的8个
     */
    public static byte[] subString8(byte[] bytes, int end) {
        byte[] newBytes = new byte[8];
        if (end > 7) {
            int index = 7;
            for (int i = end - 1; i >= 0; i--) {
                newBytes[index] = bytes[i];
                if (index == 0)
                    break;
                index--;
            }
        }
        return newBytes;
    }

    /**
     * byte数组转字符串
     *
     * @param bytein
     * @param length
     * @param addSpace 是否需要添加空格
     * @return
     */
    public static String byte2HexString(byte[] bytein, int length, boolean addSpace) {
        String string = "";
        if (length > bytein.length) return "";
        for (int i = 0; i < length; i++) {
            try {
                String rule = addSpace ? "%02X " : "%02X";
                string += String.format(rule, bytein[i]);
            } catch (Exception e) {
                break;
            }
        }
        return string;
    }

    /**
     * byte数组转字符串
     *
     * @param bytein
     * @param length
     * @return
     */
    public static String byte2HexString(byte[] bytein, int length) {
        return byte2HexString(bytein, length, true);
    }
}
