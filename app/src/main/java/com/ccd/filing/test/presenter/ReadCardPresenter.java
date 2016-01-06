package com.ccd.filing.test.presenter;

import android.media.MediaPlayer;

import com.ccd.filing.test.R;
import com.ccd.filing.test.model.SaveModel;
import com.ccd.filing.test.presenter.interfaces.BasePresenter;
import com.ccd.filing.test.view.interfaces.ReadCardIF;
import com.gpio.ex.PowerOperate;
import com.lv.tools.CardHelper;
import com.lv.tools.IDCard;
import com.lv.tools.ReadIdResult;
import com.lv.tools.ReadInfoResult;

import android_serialport_api.SerialPortHelper;
import android_serialport_api.SerialPortPath;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 15/12/31
 *         email 1179524193@qq.com
 *         </p>
 */
public class ReadCardPresenter extends BasePresenter<ReadCardIF> {
    private boolean isWorking = false;
    private SaveModel saveModel;

    @Override
    public void attachView(ReadCardIF view) {
        super.attachView(view);
        saveModel = new SaveModel();
    }

    /**
     * 读Id
     */
    public void readId() {
        if (isWorking() == false) {
//            getView().showMessage("请刷前面板");
            getView().showCardId("");
            //开启电源
            PowerOperate.getInstance().enableRfId();
            CardHelper.getCardId(
                    SerialPortHelper.getSerialPort(115200, SerialPortPath.ttyMT3, true),
                    20,
                    new ReadIdResult() {
                        @Override
                        public void onSuccess(String cardId) {
                            playMusic();
                            if (cardId.length() == 24) {
                                cardId = cardId.substring(0, 16);
                                getView().showCardId(cardId);
                            }
                            isWorking = false;
                            getView().setIdProgressButtonStatus(getView().PROGRESS_BUTTON_DEFAULT);
                        }

                        @Override
                        public void onFailure(int errorMessage) {
                            switch (errorMessage) {
                                case ReadIdResult.ERROR_PORT:
                                    getView().showMessage("串口出错");
                                    break;
                                case ReadIdResult.ERROR_TIMEOUT:
                                    getView().showMessage("读取超时");
                                    break;
                                case ReadIdResult.ERROR_UNKNOWN:
                                    getView().showMessage("未知错误");
                                    break;
                            }
                            SerialPortHelper.closeSerialPort(SerialPortPath.ttyMT3);
                            isWorking = false;
                            getView().setIdProgressButtonStatus(getView().PROGRESS_BUTTON_DEFAULT);
                        }
                    }
            );
        }
    }

    /**
     * 读卡
     */
    public void readCard() {
        if (isWorking() == false) {
//            getView().showMessage("请刷后面板");
            getView().showInfo(null);
            getView().showImage(null);
            //开启后面电源
            PowerOperate.getInstance().enableRfInfo();
            CardHelper.getCardInfo(
                    SerialPortHelper.getSerialPort(38400, SerialPortPath.ttyMT3, true),
                    30,
                    new ReadInfoResult() {
                        @Override
                        public void onFindCard() {
                            getView().setHintColor(getView().CARD_FIND);
                        }

                        @Override
                        public void onSuccess(IDCard idCard) {
                            getView().setHintColor(getView().CARD_FIND);
                            //播放提示音
                            playMusic();
                            /**
                             * 显示到界面上
                             */
                            getView().showInfo(idCard);
                            /**
                             * 保存本地
                             */
                            saveModel.saveIDCard(idCard);
                            isWorking = false;
                            getView().setInfoProgressButtonStatus(getView().PROGRESS_BUTTON_DEFAULT);
                        }

                        @Override
                        public void onFailure(int errorMessage) {
                            switch (errorMessage) {
                                case ReadInfoResult.ERROR_PORT:
                                    getView().showMessage("串口错误");
                                    break;
                                case ReadInfoResult.ERROR_TIMEOUT:
                                    getView().showMessage("读卡超时");
                                    break;
                                case ReadInfoResult.ERROR_PARSE:
                                    getView().showMessage("解析错误");
                                    break;
                                case ReadInfoResult.ERROR_UNKNOWN:
                                    getView().showMessage("未知错误");
                                    break;
                            }
                            SerialPortHelper.closeSerialPort(SerialPortPath.ttyMT3);
                            isWorking = false;
                            getView().setInfoProgressButtonStatus(getView().PROGRESS_BUTTON_DEFAULT);
                        }

                        @Override
                        public void onFindCardSuccess() {
                            getView().setHintColor(getView().CARD_SELECT);
                        }

                        @Override
                        public void onSelectCardSuccess() {
                            getView().setHintColor(getView().CARD_READ);
                        }
                    }
            );
        }
    }

    private void playMusic() {
        MediaPlayer player = MediaPlayer.create(getView().getContext(), R.raw.di);
        player.start();
    }

    private boolean isWorking() {
        if (isWorking) {
            getView().showMessage("正在工作");
        }
        return isWorking;
    }
}
