package com.lv.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPort;

/**
 * @author lvzhongyi
 * @description 读卡信息的帮助类
 * @date 2015/10/23 0023
 * @email 1179524193@qq.cn
 */
public class CardHelper {
    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    /**
     * 得到卡体管理号
     *
     * @param port       串口对象{@link SerialPort}
     * @param outTime    超时时间
     * @param readResult 读卡结果{@link ReadIdResult}
     */
    public static void getCardId(SerialPort port, int outTime, ReadIdResult readResult) {
        SERVICE.execute(new ReadIdRunnable(port, outTime, readResult));
    }

    /**
     * 得到身份证信息
     *
     * @param port           串口对象{@link SerialPort}
     * @param outTime        超时时间
     * @param readInfoResult 读卡结果{@link ReadInfoResult}
     */
    public static void getCardInfo(SerialPort port, int outTime, ReadInfoResult readInfoResult) {
        SERVICE.execute(new ReadInfoRunnable(port, outTime, readInfoResult));
    }
}
