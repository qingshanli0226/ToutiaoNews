package com.example.framework2.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.myselfview.MyLoadingBar;
import com.example.framework2.R;

/**
 * 请求网络数据Fragment
 *
 * @param <T>
 * @param <V>
 */
public abstract class BaseMVPFragment<T extends IPresenter, V extends IView> extends BaseFragment {

    protected T iHttpPresenter;
    private MyLoadingBar loadingbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (loadingbar == null) {
            loadingbar = findViewById(R.id.loadingbar);
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
    public void onDestroy() {
        super.onDestroy();
        if (iHttpPresenter != null){
            iHttpPresenter.detachView();
            iHttpPresenter = null;
        }
        if (loadingbar != null) {
            loadingbar.stopAnimation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (loadingbar != null) {
            loadingbar.stopAnimation();
        }
    }
}
