package com.ccd.filing.test.view.interfaces;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.ccd.filing.test.presenter.interfaces.BasePresenter;


/**
 * @author lvzhongyi
 *         <p>
 *         description  基础的activity
 *         date 2015/12/3 0003
 *         email 1179524193@qq.com
 *         </p>
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {
    protected P mPresenter;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 全屏显示
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPresenter = createPresenter(savedInstanceState);
        mPresenter.attachView((V) this);
        /**
         * 唤醒
         */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    protected abstract P createPresenter(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detchView();
    }

}
