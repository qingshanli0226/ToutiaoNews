package com.example.framework2.mvp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.framework2.mvp.presenter.IPresenter;

public abstract class BaseLJZFragment<P extends IPresenter> extends Fragment {
    protected View rootView;
    private boolean isInitView = false;
    private boolean isVisible = false;
    protected P mPresenter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setContentView(), container, false);
        ARouter.getInstance().inject(this);
        initView();
        isInitView = true;
        isCanLoadData();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见，获取该标志记录下来
        if (isVisibleToUser) {
            isVisible = true;
            isCanLoadData();
        } else {
            isVisible = false;
        }
    }

    private void isCanLoadData() {
        //所以条件是view初始化完成并且对用户可见
        if (isInitView && isVisible) {
            lazyLoad();

            //防止重复加载数据
            isInitView = false;
            isVisible = false;
        }
    }

    /**
     * 加载页面布局文件
     *
     * @return
     */
    protected abstract int setContentView();

    /**
     * 初始化view视图
     */
    protected abstract void initView();

    /**
     * 加载要显示的数据
     */
    protected abstract void lazyLoad();

}
