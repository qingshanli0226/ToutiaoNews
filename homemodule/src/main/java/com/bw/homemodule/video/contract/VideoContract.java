package com.bw.homemodule.video.contract;

import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.farmework.base.BasePresenter;
import com.example.farmework.base.IView;

import java.util.ArrayList;

public class VideoContract {

    public interface IVideoView extends IView{
        void onVideoData(ArrayList<VideoDataBean> videoBeans);
    }

    public static abstract class VideoPresenter extends BasePresenter<IVideoView> {
        public abstract void getVideoData(String category,long lastTime);
    }
}
