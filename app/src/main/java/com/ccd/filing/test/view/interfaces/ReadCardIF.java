package com.ccd.filing.test.view.interfaces;

import android.content.Context;
import android.graphics.Bitmap;

import com.lv.tools.IDCard;

/**
 * Created by lv on 15-7-28.
 * <p/>
 * 读卡View接口
 */
public interface ReadCardIF {
    int PROGRESS_BUTTON_DEFAULT = 0;    //进度按钮的默认状态
    int PROGRESS_BUTTON_RUN = 50;       //进度按钮的运行状态
    int PROGRESS_BUTTON_STOP = 100;     //进度按钮的停止状态

    int CARD_FIND = 0;  //寻卡
    int CARD_SELECT = 2;    //选卡
    int CARD_READ = 3;  //读卡


    /**
     * 显示身份证ID
     *
     * @param cardId 身份证ID
     */
    void showCardId(String cardId);

    /**
     * 显示身份证信息
     *
     * @param idCard 身份证信息实体
     */
    void showInfo(IDCard idCard);

    /**
     * 显示消息
     *
     * @param message 消息内容
     */
    void showMessage(String message);

    /**
     * 设置读id按钮的状态
     *
     * @param status {@link #PROGRESS_BUTTON_DEFAULT} or {@link #PROGRESS_BUTTON_RUN} or{@link #PROGRESS_BUTTON_STOP}
     */
    void setIdProgressButtonStatus(int status);

    /**
     * 设置读信息按钮的状态
     *
     * @param status {@link #PROGRESS_BUTTON_DEFAULT} or {@link #PROGRESS_BUTTON_RUN} or{@link #PROGRESS_BUTTON_STOP}
     */
    void setInfoProgressButtonStatus(int status);
    /**
     * 显示图片
     *
     * @param bitmap 图片
     */
    void showImage(Bitmap bitmap);


    /**
     * 得到上下文对象
     *
     * @return context
     */
    Context getContext();

    /**
     * 设置读卡状态的提示
     *
     * @param i {@link #CARD_FIND} or {@link #CARD_SELECT} or {@link #CARD_READ}
     */
    void setHintColor(int i);

}
