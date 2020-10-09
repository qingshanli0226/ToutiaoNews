package com.example.framework.bean;

import android.os.Bundle;

import androidx.annotation.Nullable;


public abstract class BaseMVPActivity<P extends IPresenter, V extends IView> extends BaseActivity {

    protected P iHttpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        iHttpPresenter.attachView((V) this);
        initData();
    }

    protected abstract void initPresenter();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iHttpPresenter.detachView();
}
                }
