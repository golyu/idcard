package com.lv.tools.exceptions;

/**
 * @author lvzhongyi
 * description 串口读取数据异常类
 * date 2015/10/23 0023
 * email 1179524193@qq.cn
 */
public class SerialPortException extends RuntimeException {
    /**
     * 串口对象空指针异常
     * Serial port object null pointer exception
     */
    public static final String SERIALPORT_NULL = "com.android_serialport_api.SErialPort is null";

    /**
     * 串口输入流异常错误
     * Serial inputStream exception error
     */
    public static final String SERIALPORT_INPUT_ERROR = "com.android_serialport_api.SErialPort.inputStream input error";
    /**
     * 串口输出流异常错误
     * Serial outputStream exception error
     */
    public static final String SERIALPORT_INPUT_NULL = "com.android_serialport_api.SErialPort.outputStream is null";
    /**
     * 串口输入流空指针异常
     * Serial input stream null pointer exception
     */
    public static final String SERIALPORT_OUTPUT_ERROR = "com.android_serialport_api.SErialPort.inputStream output " +
            "error";
    /**
     * 串口输出流空指针异常
     * Serial output stream null pointer exception
     */
    public static final String SERIALPORT_OUTPUT_NULL = "com.android_serialport_api.SErialPort.outputStream is null";

    /**
     * Constructor of SerialPortException.
     *
     * @param errorMessage
     */
    public SerialPortException(String errorMessage) {
        super(errorMessage);
    }
}
