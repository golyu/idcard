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
 * description 加载gpio口操作的jni
 * date 2015/10/28 0028
 * email 1179524193@qq.cn
 */
class GpioOperate {
    /**
     * 加载 libgpio_ex.so
     */
    static {
        System.loadLibrary("gpio_ex");
    }

    public static native int GetGpioMaxNumber();

    public static native boolean GPIOInit();

    public static native boolean GPIOUnInit();

    public static native boolean SetGpioOutput(int gpio_index);

    public static native boolean SetGpioDataHigh(int gpio_index);

    public static native boolean SetGpioDataLow(int gpio_index);

    public static native int GPIO_OP_GetGpioDir(int gpio_index);

    public static native int GPIO_OP_GetGpioMode(int gpio_index);

    public static native int GPIO_OP_GetGpioDataOut(int gpio_index);

    public static native int getDeviceFd();
}
