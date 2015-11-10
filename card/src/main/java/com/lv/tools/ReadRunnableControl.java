package com.lv.tools;

/**
 * @author lvzhongyi
 * description
 * date 2015/10/23 0023
 * email 1179524193@qq.cn
 */
public abstract class ReadRunnableControl {
    boolean stop = false;

    public void setStop() {
        this.stop = true;
    }
}
