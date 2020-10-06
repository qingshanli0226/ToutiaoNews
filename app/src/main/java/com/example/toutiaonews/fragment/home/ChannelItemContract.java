package com.example.toutiaonews.fragment.home;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;
import com.example.net.activity_bean.entity.NewsData;
import com.example.net.activity_bean.response.NewsResponse;

import java.util.List;

import io.reactivex.Observer;

public interface ChannelItemContract {
    interface View extends IView {
        String getCode();
        void getedData(List<NewsData> listBean);
    }
    interface Model extends IModel {
        void requestGetData(String code,long lastTime, Observer<NewsResponse> observer);
    }

}
