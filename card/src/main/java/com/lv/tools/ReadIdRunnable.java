package com.lv.tools;

import com.lv.tools.exceptions.SerialPortException;

import java.io.IOException;

import android_serialport_api.SerialPort;

/**
 * @author lvzhongyi
 *         description 读取卡iD的线程
 *         date 2015/10/23 0023
 *         email 1179524193@qq.cn
 */
public class ReadIdRunnable extends ReadRunnableControl{
    private long startTime;//开始时间
    private SerialPort serialPort;  //串口对象
    private final ReadIdResult readResult;    //返回结果对象
    private int outTime;    //超时时间，秒

    public ReadIdRunnable(SerialPort serialPort, int outTime, ReadIdResult readResult) {
        if (serialPort == null) {
            throw new SerialPortException(SerialPortException.SERIALPORT_NULL);
        }
        if (serialPort.getInputStream() == null) {
            throw new SerialPortException(SerialPortException.SERIALPORT_INPUT_NULL);
        }
        if (serialPort.getOutputStream() == null) {
            throw new SerialPortException(SerialPortException.SERIALPORT_OUTPUT_NULL);
        }
        this.serialPort = serialPort;
        this.readResult = readResult;
        startTime = System.currentTimeMillis();
        this.outTime = outTime;
    }

    @Override
    public void run() {
        byte[] data = new byte[12];
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {

        }
        try {
            clearSerialPortData(serialPort.getInputStream());
        } catch (IOException e) {

        }
        while (true) {
            if ((System.currentTimeMillis() - startTime) > (outTime * 1000)) {
                readResult.onFailure(ReadIdResult.ERROR_TIMEOUT);
                break;
            }
            if (stop) {
                break;
            }
            try {
                if (serialPort.getInputStream().available() > 0) {
                    int dateSize = serialPort.getInputStream().read(data);
                    if (dateSize == 12) {
                        String result = ConvertUtil.byte2HexString(data, false).trim();
                        if (!result.equals("C9EDB7DDD6A4CBA2BFA8B0E5") && !result.equals("B3CCD0F2B3F5CABCBBAFCDEA")) {
                            readResult.onSuccess(result);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                readResult.onFailure(ReadIdResult.ERROR_UNKNOWN);
            }

        }
    }
}
