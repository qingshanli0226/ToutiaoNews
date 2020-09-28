package com.example.framework.bean;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 定义Activity的基类，在里面定义抽象方法，抽象方法按照一定时序调用。并且在基类中定义方法，让子类复用
 */
public abstract class BaseActivity extends AppCompatActivity {

    String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏是白底黑色
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(getLayoutId());
        initView();
        TAG = "LQS:" + getClass().getSimpleName();
    }

    //子类需要实现的抽象方法
    protected abstract void initView();

    protected abstract int getLayoutId();

}
