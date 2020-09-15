package com.bw.homemodule.home.view;

import android.widget.TextView;
import android.widget.Toast;

import com.bw.homemodule.R;
import com.bw.homemodule.home.contract.HomeContract;
import com.bw.homemodule.home.presenter.HomePresenterImpl;
import com.example.common.entity.NewsData;
import com.example.common.response.NewsResponse;
import com.example.farmework.base.BaseMVPFragment;

import java.util.List;

public class DebugFragment extends BaseMVPFragment<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView {
    private String channel;
    private String channel_code;

    public DebugFragment(String channel, String channel_code) {
        this.channel = channel;
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {
        mPresenter.getHomeData(channel_code);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HomePresenterImpl();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_debug;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        TextView textView = findViewById(R.id.show);
        textView.setText(channel + "");
    }


    @Override
    public void onHomeData(NewsResponse newsResponse) {
        List<NewsData> data = newsResponse.data;
        Toast.makeText(mActivity, "" + data.get(0).content, Toast.LENGTH_SHORT).show();

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
