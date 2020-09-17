package com.example.video.mvp.contract;

import com.example.common.bean.NewsRoomBean;
import com.example.common.entity.Video;
import com.example.common.entity.VideoBean;
import com.example.common.response.NewsResponse;
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
