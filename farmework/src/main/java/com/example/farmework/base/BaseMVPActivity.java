package com.example.farmework.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.farmework.listener.PermissionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseMVPActivity<T extends IPresenter, V extends IView> extends BaseActivity {
    protected T iHttpPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        iHttpPresenter.attachView((V) this);
        initHttpData();
    }

    protected abstract void initPresenter();
    protected abstract void initHttpData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(iHttpPresenter != null){
            iHttpPresenter.detachView();
        }
    }


}
