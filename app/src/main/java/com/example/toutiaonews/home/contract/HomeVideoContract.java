package com.example.toutiaonews.home.contract;

import com.example.common.mode.VideoBean;
import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;

public class HomeVideoContract {

    public interface HomeVideoView extends IView {
        void onViewData(VideoBean videoBean);
    }

    public static abstract class HomeVideoPresenter extends BasePresenter<HomeVideoView>{
        public abstract void getVideoData(String category);
    }
}