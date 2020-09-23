package com.example.home.contract;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;
import com.example.net.bean.Recommend;
import com.example.net.observer.BaseObserver;

public interface ContractRecommend {
    interface View extends IView {
        void getRecommendData(Recommend recommendBean);
    }

    interface Model extends IModel {
        void getData(BaseObserver<Recommend> baseObserver, long listTime, String index);
    }
}
