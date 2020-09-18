package com.example.framework2.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.common.myselfview.MyLoadingBar;
import com.example.framework2.R;


/**
 * 请求网络数据Activity
 *
 * @param <T>
 * @param <V>
 */
public abstract class BaseMVPActivity<T extends IPresenter, V extends IView> extends BaseActivity {

    protected T iHttpPresenter;

    private MyLoadingBar loadingBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (loadingBar == null) {
            loadingBar = findViewById(R.id.loadingbar);
        }

        initPresenter();
        iHttpPresenter.attachView((V) this);
        initHttpData();
    }

    //请求网络数据
    protected abstract void initHttpData();

    //初始化presenter
    protected abstract void initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (iHttpPresenter != null){
            iHttpPresenter.detachView();
            iHttpPresenter = null;
        }
        if (loadingBar != null) {
            loadingBar.stopAnimation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadingBar != null) {
            loadingBar.stopAnimation();
        }
    }

}
