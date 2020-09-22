package com.example.framework2.mvp.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.framework2.mvp.presenter.IPresenter;


public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity, IView, View.OnClickListener {
    protected P mPresenter;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bandLayout());
        ARouter.getInstance().inject(this);

        initView();
        initData();
    }

    @Override
    public void showView() {

    }

    @Override
    public void hideView() {

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}