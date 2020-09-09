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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(bandLayout(), container,false);
            initPresenter();
            mPresenter.attachView((V) this);
        }else{
            //得到容器视图
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }
    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        initHttpData();
    }
    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return rootView;
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
