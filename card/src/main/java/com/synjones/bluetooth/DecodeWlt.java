package com.synjones.bluetooth;

/**
 * @author lvzhongyi
 * description
 * date 2015/10/23 0023
 * email 1179524193@qq.cn
 */
public class DecodeWlt {
    static {
        System.loadLibrary("DecodeWlt");
    }
    public DecodeWlt(){

    }

    public native int Wlt2Bmp(String var1, String var2);
}
