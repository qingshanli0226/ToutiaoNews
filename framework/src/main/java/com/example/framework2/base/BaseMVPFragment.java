package com.example.framework2.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
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

    protected boolean isUserVisible = false; //通过该变量判断当前Fragment是否对用户可见，如果可见是true否则为false
    protected boolean isViewCreated = false;  //通过改变量判断当前的View是否创建成功，创建成功则为true否则为false

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (loadingBar == null) {
            loadingBar = findViewById(R.id.loadingbar);
        }

        initPresenter();
        iHttpPresenter.attachView((V) this);

        isViewCreated = true;
        initHttpData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        //把此Fragment的可见状态付给是否是第一次用户可见此Fragment
        CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISLOOK,this.isUserVisible);
        if(isVisibleToUser){
            //储存用户可见的时间戳
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.USERLOOKTIME,String.valueOf(System.currentTimeMillis()));
        }
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
    public void onResume() {
        super.onResume();
        //当此Fragment不与用户可见时 更新第一次进行网络请求的时间戳
        CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.ONETIME,String.valueOf(System.currentTimeMillis()));
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
