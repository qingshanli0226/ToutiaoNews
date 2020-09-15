package com.example.farmework.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseMVPFragment<P extends IPresenter,V extends IView> extends BaseFragment {
    protected P mPresenter;
    private View rootView;
    protected Activity mActivity;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        initHttpData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
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
        rootView = null;
    }
}
