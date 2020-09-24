package com.example.framework2.base;

import android.content.Context;
import android.net.ConnectivityManager;
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
    private MyLoadingBar loadingBar;

    private ConnectivityManager manager;//判断网络连接

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    public void onDestroy() {
        super.onDestroy();
        if (iHttpPresenter != null) {
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

    //调用该方法判断网络状态--->返回一个boolean
    protected boolean checkNetworkState() {
        boolean flag = false;
        //得到网络连接信息
        manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            flag = manager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }


}
