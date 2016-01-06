package com.ccd.filing.test.presenter.interfaces;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author lvzhongyi
 *         <p>
 *         description 基础的presenter
 *         date 2015/12/3 0003
 *         email 1179524193@qq.com
 *         </p>
 */
public abstract class BasePresenter<V> {
    private Reference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detchView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
