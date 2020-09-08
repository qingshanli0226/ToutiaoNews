package com.example.toutiaonews.fragment_video.contract;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;

public interface ContractVideo {
    interface View extends IView {
        void getVideoData();
    }

    interface Model extends IModel {

    }
}
