package com.example.toutiaonews.video.contract;

import com.example.common.mode.VideoBean;
import com.example.framework2.base.BasePresenter;
import com.example.framework2.base.IView;

public class NewsVideoContract {

    public interface IVideoView extends IView {
        void onVideoData(VideoBean videoBean);
    }

    public static abstract class NewsVideoPresenter extends BasePresenter<IVideoView> {
        public abstract void getNewsVideoData(String category,long currentTime);
    }


}
