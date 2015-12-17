package com.lv.tools;

import android.util.Log;

import com.lv.tools.exceptions.SerialPortException;

import java.io.IOException;
import java.util.Arrays;

import android_serialport_api.SerialPort;

/**
 * @author lvzhongyi
 *         description 读取卡信息的线程
 *         date 2015/10/23 0023
 *         email 1179524193@qq.cn
 */
public class ReadInfoRunnable extends ReadRunnableControl {
    private long startTime;//开始时间
    private SerialPort serialPort;  //串口对象
    private final ReadInfoResult readInfoResult;    //返回结果对象
    private int outTime;    //超时时间，秒

    public ReadInfoRunnable(SerialPort serialPort, int outTime, ReadInfoResult readInfoResult) {
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
        this.readInfoResult = readInfoResult;
        startTime = System.currentTimeMillis();
        this.outTime = outTime;
    }

    @Override
    public void run() {
        while (true) {
            if (stop) {
                Log.v("xxxxx", "stop");
                break;
            }
            Log.v("xxxxx", "info");
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//
//            }
            if ((System.currentTimeMillis() - startTime) > (outTime * 1000)) {
                readInfoResult.onFailure(ReadInfoResult.ERROR_TIMEOUT);
//                Log.v("card_id", "时间到");
                break;
            }
            try {
                readInfoResult.onFindCard();
                if (findCard()) {
                    readInfoResult.onFindCardSuccess();
//                    Log.v("card_id", "寻卡成功");
                    if (selectCard()) {
                        readInfoResult.onSelectCardSuccess();
//                        Log.v("card_id", "选卡成功");
                        byte[] result = readCard();
                        if (result == null || result.length < 11) {
//                            Log.v("card_id", "结果为空");
                        } else if (Arrays.equals(result, Cmd.READ_CARD_INFO_FAILURE_CMD)) {
//                            Log.v("card_id", "读卡失败");
                        } else if (Arrays.equals(result, Cmd.ERROR_UNKOWN_CMD)) {
//                            Log.v("card_id", "未知错误");
                            /**
                             * 清理串口
                             */
                            clearSerialPortData(serialPort.getInputStream());
                        } else if (result[7] != (byte) 0x00 || result[8] != (byte) 0x00 || result[9] != (byte) 0x90) {
//                            Log.v("card_id", "操作失败");
//                            Status = 2;
//                            IDCard.SW1 = recvl[7];
//                            IDCard.SW2 = recvl[8];
//                            IDCard.SW3 = recvl[9];
//                            L.v("sw3=" + Integer.toHexString(IDCard.SW3));
                        } else if (result.length < 1295) {
//                            Log.v("card_id", "数据不全");
                        } else {
                            byte[] waitParse = new byte[4 + 256 + 1024];
                            System.arraycopy(result, 10, waitParse, 0, waitParse.length);
                            //Log.i("串口调试", "basemsg="+basemsg.length);
                            try {
                                IDCard idCard = ParseCardInfo.parse(waitParse);
                                setStop();
                                readInfoResult.onSuccess(idCard);
//                                Log.v("card_id", "" + ConvertUtil.byte2HexString(result));
                                return;
                            } catch (Exception e) {
                                readInfoResult.onFailure(ReadInfoResult.ERROR_PARSE);
                                return;
                            }
//                            Log.v("card_id", "读卡成功");

                        }
                    } else {
//                        Log.v("card_id", "选卡失败");
                    }
                } else {
//                    Log.v("card_id", "寻卡失败");
                }
            } catch (IOException e) {
                readInfoResult.onFailure(ReadInfoResult.ERROR_UNKNOWN);
            }

        }
    }

    /**
     * 读卡器复位
     *
     * @return true》》成功，false》》失败
     * @throws IOException
     */
    private boolean samReset() throws IOException {
        Log.v("card", "开始复位");
        byte[] result = sendCmd(Cmd.SAM_RESET_CMD, Cmd.SAM_RESET_SUCCESS_CMD.length);
        if (Arrays.equals(result, Cmd.SAM_RESET_SUCCESS_CMD)) {
            Log.v("card", "复位成功"+ConvertUtil.byte2HexString(result));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            Log.v("card", "复位失败"+ConvertUtil.byte2HexString(result));
            return false;
        }
    }

    /**
     * 寻卡
     *
     * @return true》》成功，false》》失败
     * @throws IOException
     */
    private boolean findCard() throws IOException {
        Log.v("card_id", "开始寻卡");
        byte[] result = sendCmd(Cmd.FIND_CARD_CMD, Cmd.FIND_CARD_SUCCESS_CMD.length);
        Log.v("card_id", ConvertUtil.byte2HexString(result));
        if (Arrays.equals(result, Cmd.FIND_CARD_SUCCESS_CMD)) {
            return true;
        } else {
            samReset();
            return false;
        }
    }

    /**
     * 选卡
     *
     * @return true》》成功，false》》失败
     * @throws IOException
     */
    private boolean selectCard() throws IOException {
//        Log.v("card_id", "开始选卡");
        byte[] result = sendCmd(Cmd.SELECT_CARD_CMD, Cmd.SELECT_CARD_SUCCESS_CMD.length);
//        Log.v("card_id", ConvertUtil.byte2HexString(result));
        if (Arrays.equals(result, Cmd.SELECT_CARD_SUCCESS_CMD))
            return true;
        else
            return false;
    }

    /**
     * 读卡
     *
     * @return 返回读卡数据
     * @throws IOException
     */
    private byte[] readCard() throws IOException {
//        Log.v("card_id", "开始读卡");
        byte[] result = sendCmd(Cmd.READ_CARD_INFO_CMD, 2000);
//        Log.v("card_id", ConvertUtil.byte2HexString(result));
        return result;
    }

    /**
     * 写入命令，收集返回数据
     *
     * @param cmd         命令
     * @param resultLeght 返回结果的长度
     * @return 收集到的数据
     * @throws IOException
     */
    private synchronized byte[] sendCmd(byte[] cmd, int resultLeght) throws IOException {

        byte[] data = new byte[resultLeght];
        byte[] temp = new byte[resultLeght];

        /**
         * 清理串口数据
         */
        clearSerialPortData(serialPort.getInputStream());

        /**
         * 写入寻卡命令
         */
        serialPort.getOutputStream().write(cmd);
        try {
            Thread.sleep((long) (Math.random()*200+300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long readStartTime = System.currentTimeMillis();

        //data的下标
        int index = 0;
//        Log.v("card", "开始读");
//        Log.v("card", "" + serialPort.getInputStream().available());
        while (serialPort.getInputStream().available() > 0 || (index < 7) || (index > 7 && (index < data[5] * 256 +
                data[6] + 7))) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            long take = System.currentTimeMillis() - readStartTime;
            if ((take > 3000) || ((index > 5) && (data[0] != (byte) 0xAA || data[1] != (byte) 0xAA || data[2] !=
                    (byte) 0xAA || data[3] != (byte)
                    0x96 || data[4] != (byte) 0x69))) {
//                Log.v("card", "进入eroor" + take + ">>" + ConvertUtil.byte2HexString(data));
                return Cmd.ERROR_UNKOWN_CMD;
            }
            if (serialPort.getInputStream().available() > 0) {
                int dataSize = serialPort.getInputStream().read(temp);
                for (int i = 0; i < dataSize; i++) {
                    if (index < data.length) {
                        data[index] = temp[i];
                        index++;
                    }
                }
            }
        }
//        System.out.println("card 出while");

        byte[] result = new byte[index];
        System.arraycopy(data, 0, result, 0, result.length);
        return result;
    }

}
