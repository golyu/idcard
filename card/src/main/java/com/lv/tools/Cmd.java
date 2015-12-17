package com.lv.tools;

/**
 * @author lvzhongyi
 *         description 得到命令
 *         date 2015/10/23 0023
 *         email 1179524193@qq.cn
 */
public class Cmd {
    /**
     * 寻卡命令
     * 10
     */
    public static final byte[] FIND_CARD_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte) 0x69,
            (byte) 0x00, (byte) 0x03, (byte) 0x20, (byte) 0x01, (byte) 0x22};
    /**
     * 寻卡成功
     * 15
     */
    public static final byte[] FIND_CARD_SUCCESS_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte)
            0x69, (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x9F, (byte) 0x00, (byte) 0x00, (byte)
            0x00, (byte) 0x00, (byte) 0x97};
    /**
     * 寻卡失败
     * 11
     */
    public static final byte[] FIND_CARD_FAILURE_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte)
            0x69, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x84};
    /**
     * 选卡命令
     * 10
     */
    public static final byte[] SELECT_CARD_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte) 0x69,
            (byte) 0x00, (byte) 0x03, (byte) 0x20, (byte) 0x02, (byte) 0x21};
    /**
     * 选卡成功
     * 19
     */
    public static final byte[] SELECT_CARD_SUCCESS_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte)
            0x69, (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x00, (byte) 0x00, (byte)
            0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x9C};
    /**
     * 选卡失败
     * 11
     */
    public static final byte[] SELECT_CARD_FAILURE_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte)
            0x69, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x81, (byte) 0x85};
    /**
     * 读取信息命令
     * 10
     */
    public static final byte[] READ_CARD_INFO_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte) 0x69,
            (byte) 0x00, (byte) 0x03, (byte) 0x30, (byte) 0x01, (byte) 0x32};
    /**
     * 读取信息失败命令
     * 11
     */
    public static final byte[] READ_CARD_INFO_FAILURE_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96,
            (byte) 0x69, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x41, (byte) 0x45};

    /**
     * 复位命令
     * 10
     */
    public static final byte[] SAM_RESET_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte) 0x69, (byte)
            0x00, (byte) 0x03, (byte) 0x10, (byte) 0xFF, (byte) 0xEC};

    /**
     * 复位完成命令
     * 11
     */
    public static final byte[] SAM_RESET_SUCCESS_CMD = {(byte) 0xAA, (byte) 0xAA, (byte) 0xAA, (byte) 0x96, (byte) 0x69, (byte) 0x00, (byte) 0x04,
            (byte) 0x00, (byte) 0x00, (byte) 0x90, (byte) 0x94};

    /**
     * 自定义的错误命令
     * 1
     */
    public static final byte[] ERROR_UNKOWN_CMD = {(byte) 0x01};


}
