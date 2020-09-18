package com.example.video.fragment_video.contract;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;
import com.example.net.bean.Recommend;
import com.example.net.observer.BaseObserver;

public interface ContractVideo {
    interface View extends IView {
        void getVideoData(Recommend videoBean);
    }

    interface Model extends IModel {
        void getData(BaseObserver<Recommend> baseObserver, long listTime, String index);
    }
}
