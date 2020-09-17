package com.example.toutiaonews.home.contract;

import com.example.common.mode.HomeVideoBean;
import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;

public class HomeVideoContract {

    public interface HomeVideoView extends IView {
        void onViewData(HomeVideoBean homeVideoBean);
    }

    public static abstract class HomeVideoPresenter extends BasePresenter<HomeVideoView>{
        public abstract void getVideoData(String category);
    }
}
