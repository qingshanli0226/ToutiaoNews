package com.example.framework2.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 请求网络数据Fragment
 * @param <T>
 * @param <V>
 */
public abstract class BaseMVPFragment<T extends IPresenter, V extends IView> extends BaseFragment {

    protected T ihttpPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initPresenter();
        ihttpPresenter.attachView((V) this);
        initHttpData();
    }

    //请求网络数据
    protected abstract void initHttpData();

    //初始化presenter
    protected abstract void initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpPresenter.detachView();
    }

}
