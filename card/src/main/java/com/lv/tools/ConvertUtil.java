package com.lv.tools;

/**
 * Created by lv on 15-7-28.
 */
public class ConvertUtil {
    /**
     * byte数组转字符串
     *
     * @param bytein   byte类型数组
     * @param addSpace 是否需要添加空格
     * @return 转换后的字符串
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
     * @param bytein byte类型数组
     * @return 转换后的字符串
     */
    public static String byte2HexString(byte[] bytein) {
        return byte2HexString(bytein, true);
    }

    /**
     * byte数组转字符串
     *
     * @param bytein   byte类型数组
     * @param length   需要转换的长度，长度后面的数据丢掉
     * @param addSpace 是否需要添加空格
     * @return 转换后的字符串
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
     * @param bytein byte类型数组
     * @param length 需要转换的长度，长度后面的数据丢掉
     * @return 转换后的字符串
     */
    public static String byte2HexString(byte[] bytein, int length) {
        return byte2HexString(bytein, length, true);
    }
}
