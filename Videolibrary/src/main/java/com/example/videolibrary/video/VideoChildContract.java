package com.example.videolibrary.video;

import com.example.framework.bean.BasePresenter;
import com.example.framework.bean.IView;
import com.example.net.activity_bean.VideoBean;

public class VideoChildContract {

    public interface IVideoChildView extends IView {
        void onVideoChildData(VideoBean videoBean);
    }

    public static abstract class IVideoChildPresenter extends BasePresenter<IVideoChildView> {
        public abstract void getVideoChildData(String category);
    }


}
