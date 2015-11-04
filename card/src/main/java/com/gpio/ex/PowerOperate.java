/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gpio.ex;

/**
 * @author lvzhongyi
 * description 拉高和拉低相应的GPIO口来实现电流控制，控制给读ID的模块通电或者读身份证信息的模块通电
 * date 2015/10/28 0028
 * email 1179524193@qq.cn
 */
public class PowerOperate {


    private static PowerOperate mPowerOperate;

    /**
     * private constructor suppresses
     */
    private PowerOperate() {

    }

    /**
     * get this current instance
     *
     * @return this {@link #mPowerOperate}
     */
    public static PowerOperate getInstance() {
        if (mPowerOperate == null) {
            synchronized (PowerOperate.class) {
                if (mPowerOperate == null) {
                    mPowerOperate = new PowerOperate();
                }
            }
        }
        return mPowerOperate;
    }

    /**
     * 给读ID的模块供电
     */
    public void enableRfId() {
        gpioHelper(
                new int[]{12, 8, 43},
                new int[]{13, 44, 45}
        );
    }

    /**
     * 关闭总电源
     */
    public void disableGPIO() {
        gpioHelper(
                new int[]{},
                new int[]{44, 45, 13, 12, 8}
        );
    }

    /**
     * 给读信息的模块供电
     */
    public void enableRfInfo() {
        gpioHelper(
                new int[]{8, 44, 45},
                new int[]{12, 13, 43}
        );
    }

    /**
     * 拉高或者拉低GPIO口的帮助
     *
     * @param high 需要拉高的一些GPIO口
     * @param low  需要拉低的一些GPIO口
     */
    private synchronized void gpioHelper(int[] high, int[] low) {
        if (high.length == 0 && low.length == 0)
            return;
        GpioOperate.GPIOInit();
        for (int gpio : high) {
            if (gpio < 0) {
                GpioOperate.GPIOUnInit();
                return;
            }
            GpioOperate.SetGpioOutput(gpio);
            GpioOperate.SetGpioDataHigh(gpio);
        }
        for (int gpio : low) {
            if (gpio < 0) {
                GpioOperate.GPIOUnInit();
                return;
            }
            GpioOperate.SetGpioOutput(gpio);
            GpioOperate.SetGpioDataLow(gpio);
        }
        GpioOperate.GPIOUnInit();
    }
}
