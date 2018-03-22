package com.redli.rxjava2retrofittmvp.base;


import com.redli.rxjava2retrofittmvp.mvp.IPresenter;
import com.redli.rxjava2retrofittmvp.mvp.IView;

/**
 * @author RedLi
 * @date
 */

public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V mView;
    @Override
    public void attachView(V view) {
        mView=view;
    }

    @Override
    public void detachView() {
        mView=null;
    }
}
