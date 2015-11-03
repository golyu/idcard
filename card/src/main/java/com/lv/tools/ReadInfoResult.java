package com.lv.tools;

/**
 * @author lvzhongyi
 * @description 读卡信息结果
 * @date 2015/10/23 0023
 * @email 1179524193@qq.cn
 */
public interface ReadInfoResult {
    int ERROR_PORT = 0;    //串口错误
    int ERROR_TIMEOUT = 1; //超时
    int ERROR_PARSE = 2; //解析错误
    int ERROR_UNKNOWN = 3; //未知错误

    /**
     * 扫描成功，返回卡体信息
     *
     * @param idCard
     */
    void onSuccess(IDCard idCard);

    /**
     * 失败，返回错误信息
     *
     * @param errorMessage 错误信息{@link #ERROR_PORT} or{@link #ERROR_TIMEOUT}or{@link #ERROR_PARSE}or{@link #ERROR_UNKNOWN}
     */
    void onFailure(int errorMessage);

    /**
     * 寻卡成功
     */
    void onFindCardSuccess();

    /**
     * 选卡成功
     */
    void onSelectCardSuccess();
}
