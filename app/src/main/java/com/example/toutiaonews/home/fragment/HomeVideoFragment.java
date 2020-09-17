package com.example.toutiaonews.home.fragment;

import com.example.common.mode.HomeVideoBean;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.contract.HomeVideoContract;
import com.example.toutiaonews.home.presenter.HomeVideoPresenterImpl;

public class HomeVideoFragment extends BaseMVPFragment<HomeVideoPresenterImpl, HomeVideoContract.HomeVideoView> implements HomeVideoContract.HomeVideoView {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initHttpData() {
        //获取数据
        iHttpPresenter.getVideoData("video");
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new HomeVideoPresenterImpl();
    }

    @Override
    public void onViewData(HomeVideoBean homeVideoBean) {
        printLog(homeVideoBean.getMessage());
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
