package com.idcard.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gpio.ex.PowerOperate;
import com.lv.tools.CardHelper;
import com.lv.tools.IDCard;
import com.lv.tools.ReadIdResult;
import com.lv.tools.ReadInfoResult;

import android_serialport_api.SerialPort;
import android_serialport_api.SerialPortHelper;
import android_serialport_api.SerialPortPath;

/**
 * @author lvzhongyi iccard项目的示例
 * description
 * date 2015/11/3 0003
 * email 1179524193@qq.cn
 */
public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_layout);
    }

    /**
     * 得到串口对象
     */
    public SerialPort getSerialPort() {
        //波特率   串口号     是否new一个新的，false则从缓存中找
        return SerialPortHelper.getSerialPort(115200, SerialPortPath.ttyMT3, true);
    }

    /**
     * 打开id电源
     */
    public void openGPIOPowerId() {
        PowerOperate.getInstance().enableRfId();
    }

    /**
     * 打开info电源
     */
    public void openGPIOPowerInfo() {
        PowerOperate.getInstance().enableRfInfo();
    }

    /**
     * 关闭电源
     */
    public void closeGPIOPower() {
        PowerOperate.getInstance().disableGPIO();
    }

    /**
     * 读取身份证id
     */
    public void readIDCardId() {
        CardHelper.getCardId(getSerialPort(), 30, new ReadIdResult() {
            /**
             *
             * @param cardId
             */
            @Override
            public void onSuccess(String cardId) {
                //成功
            }

            @Override
            public void onFailure(int errorMessage) {
                //失败
            }
        });
    }

    /**
     * 读取身份证信息
     */
    public void readIDCardInfo() {
        CardHelper.getCardInfo(getSerialPort(), 30, new ReadInfoResult() {

            @Override
            public void onFindCard() {
                //开始寻卡
            }

            @Override
            public void onSuccess(IDCard idCard) {
                //成功
            }

            @Override
            public void onFailure(int errorMessage) {
                //失败
            }

            @Override
            public void onFindCardSuccess() {
                //寻卡成功
            }

            @Override
            public void onSelectCardSuccess() {
                //选卡成功
            }
        });
    }

}
