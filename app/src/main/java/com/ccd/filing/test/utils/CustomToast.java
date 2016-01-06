package com.ccd.filing.test.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ccd.filing.test.R;

/**
 * @author lvzhongyi
 * @description 自定义的Toast
 * @date 15-9-11
 * @email 1179524193@qq.cn
 */
public class CustomToast {
    /**
     * 显示的Toast
     */
    private Toast mToast;

    /**
     * 显示的Toast的视图
     */
    private View toastView;

    /**
     * 标题控件
     */
    private TextView titleView;
    /**
     * 内容控件
     */
    private TextView contentView;


    private Handler mHandler = new Handler();
    /**
     * 使Toast消失
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mToast.cancel();
            mToast = null;
        }
    };
    /**
     * 持续时间
     */
    private int duration;

    private CustomToast() {

    }

    /**
     * 设置Toast的视图，显示时间
     *
     * @param context
     * @param layoutId
     * @param duration
     */
    public CustomToast(Context context, int layoutId, int duration) {
        mHandler.removeCallbacks(runnable);
        if (mToast == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            toastView = inflater.inflate(layoutId, null);

            titleView = (TextView) toastView.findViewById(R.id.title);
            contentView = (TextView) toastView.findViewById(R.id.content);

            mToast = new Toast(context);

            setGravity(Gravity.CENTER, 0, 0);
            mToast.setView(toastView);

            this.duration = duration;

        }
    }


    /**
     * 设置在该通知应该出现在屏幕的位置
     *
     * @param gravity 位置
     * @param xOffset 水平偏移，如果参数一为TOP,LEFT,BOTTOM,CENTER,为向右偏移,RIGHT为向左偏移
     * @param yOffset 垂直偏移，如果参数一为TOP,LEFT,RIGHT,为向下偏移，BOTTOM为向上偏移
     */
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast == null) {
            throw new NullPointerException();
        }
        mToast.setGravity(gravity, xOffset, yOffset);
    }

    /**
     * 设置标题
     *
     * @param text
     * @return
     */
    public CustomToast setTitle(String text) {
        titleView.setText(text);
        return this;
    }

    /**
     * 设置内容
     *
     * @param text
     * @return
     */
    public CustomToast setContent(String text) {
        contentView.setText(text);
        return this;
    }

    /**
     * 给内容设置背景
     *
     * @param drawable 背景资源
     * @return
     */
    public CustomToast setContentBackground(Drawable drawable) {
        contentView.setBackground(drawable);
        return this;
    }

    /**
     * 给内容设置背景
     *
     * @param res 背景资源id
     * @return
     */
    public CustomToast setContentBackgroundResource(int res) {
        contentView.setBackgroundResource(res);
        return this;
    }


    /**
     * 显示Toast
     */
    public void show() {
        if (mToast == null) {
            throw new NullPointerException();
        }
        mHandler.postDelayed(runnable, duration);
        
        mToast.setDuration(Toast.LENGTH_SHORT);
        for (int i = 0; i < Math.ceil(duration/2500);i++) {
            mToast.show();
        }
    }


}
