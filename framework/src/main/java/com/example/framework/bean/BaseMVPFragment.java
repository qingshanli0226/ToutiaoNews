package com.example.framework.bean;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseMVPFragment<P extends IPresenter, V extends IView> extends BaseFragment {
    protected P ihttpPresenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        ihttpPresenter.attachView((V) this);
        initHttpData();
    }

    protected abstract void initHttpData();

    protected abstract void initPresenter();


    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpPresenter.detachView();
    }
}
