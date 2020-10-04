package com.example.video.fragment_video.contract;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;
import com.example.net.bean.Recommend;
import com.example.net.bean.VideoEntity;
import com.example.net.observer.BaseObserver;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public interface ContractVideo {
    interface View extends IView {
        void getVideoData(VideoEntity videoBean);
    }

    interface Model extends IModel {
        void getData(BaseObserver<VideoEntity> baseObserver, Consumer<Disposable> consumer, long listTime, String index);
    }
}
