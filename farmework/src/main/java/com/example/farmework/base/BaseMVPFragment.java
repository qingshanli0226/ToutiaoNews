package com.example.farmework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.cache.CacheManager;

public abstract class BaseMVPFragment<P extends IPresenter,V extends IView> extends BaseFragment {
    protected P mPresenter;
    private boolean isUserVisible = false; //通过该变量判断当前Fragment是否对用户可见，如果可见是true否则为false

    private boolean isViewCreated = false;  //通过改变量判断当前的View是否创建成功，创建成功则为true否则为false
    //如果要获取网络数据，上面两个条件都需要为true
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        mPresenter.attachView((V) this);
        isViewCreated = true;
        prepareLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        prepareLoadData();
    }

    private void prepareLoadData() {
        if (isUserVisible && isViewCreated) {
            //去获取数据
            initHttpData();
        }
    }

    protected abstract void initHttpData();

    protected abstract void initPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int bandLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
    }

}
