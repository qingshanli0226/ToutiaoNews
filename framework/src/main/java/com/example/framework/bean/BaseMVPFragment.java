package com.example.framework.bean;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseMVPFragment<P extends IPresenter, V extends IView> extends BaseFragment {
    protected P ihttpPresenter;

    private boolean isUserVisible = false;
    private boolean isViewCreated = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        ihttpPresenter.attachView((V) this);
//        initHttpData();
        isViewCreated = true;
        loadNetWorkData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isUserVisible = isVisibleToUser;
        loadNetWorkData();
    }

    protected void loadNetWorkData() {
        if (isUserVisible && isViewCreated) {
            initHttpData();
        }
    }

    protected abstract void initHttpData();

    protected abstract void initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ihttpPresenter.detachView();
    }
}
