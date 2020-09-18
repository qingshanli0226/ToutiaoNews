package com.example.videomodule.video.contract;

import com.example.common.entity.VideoBean;
import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;

public interface VideoContract {
    interface IVideoView extends IView{
        void onVideoData(VideoBean videoBean);
    }

    abstract class VideoPresenter extends BasePresenter<IVideoView>{
        public abstract void getVideoData(String category);
    }
}
