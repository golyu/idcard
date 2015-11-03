package android_serialport_api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author lvzhongyi
 * @description 串口帮助类，用来得到、保存和关闭串口，并使用hashMap来缓存串口
 * @date 2015/10/28 0028
 * @email 1179524193@qq.cn
 */
public class SerialPortHelper {


    private static final HashMap<String, SerialPort> portMap = new HashMap<>();

    /**
     * 打开串口得到对象,默认创建新的串口对象
     *
     * @param baudrate 波特率
     * @param path     串口的路径，{@link SerialPort.ttyMT0}or {@link SerialPort.ttyMT1}or{@link SerialPort.ttyMT2}or{@link
     *                 SerialPort.ttyMT3}or{@link SerialPort.ttyS0}or{@link SerialPort.ttyS1}or{@link SerialPort.ttyS2}or{@link SerialPort.ttyS3}
     * @return
     */
    public static SerialPort getSerialPort(int baudrate, String path) {
        return getSerialPort(baudrate, path, true);
    }

    /***
     * 打开串口得到对象，如果已经存在指定串口的对象，根据isNew来决定需不需要新的串口对象
     *
     * @param baudrate 波特率
     * @param path     串口的路径，{@link SerialPort.ttyMT0}or {@link SerialPort.ttyMT1}or{@link SerialPort.ttyMT2}or{@link
     *                 SerialPort.ttyMT3}or{@link SerialPort.ttyS0}or{@link SerialPort.ttyS1}or{@link SerialPort.ttyS2}or{@link SerialPort.ttyS3}
     * @param isNew    true 新的串口对象
     * @return
     */
    public static SerialPort getSerialPort(int baudrate, String path, boolean isNew) {
        if (isNew) {
            portMap.remove(path);
        }
        if (portMap.get(path) == null) {
            try {
                SerialPort serialPort = new SerialPort(new File(path), baudrate, 0);
                portMap.put(path, serialPort);
                return serialPort;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return portMap.get(path);
        }
    }

    /**
     * 关闭串口
     *
     * @param path 串口的路径，{@link SerialPort.ttyMT0}or {@link SerialPort.ttyMT1}or{@link SerialPort.ttyMT2}or{@link
     *             SerialPort.ttyMT3}or{@link SerialPort.ttyS0}or{@link SerialPort.ttyS1}or{@link SerialPort.ttyS2}or{@link SerialPort.ttyS3}
     */
    public static void closeSerialPort(String path) {
        SerialPort serialPort = portMap.get(path);
        if (serialPort != null) {
            serialPort.closeAll();
            portMap.remove(path);
        }
    }

}
