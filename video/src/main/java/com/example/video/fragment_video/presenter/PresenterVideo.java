package com.example.video.fragment_video.presenter;


import android.util.Log;

import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.net.bean.Recommend;
import com.example.net.http.HttpManager;
import com.example.net.observer.BaseObserver;
import com.example.video.bean.SqlBean;
import com.example.video.dao.DaoManager;
import com.example.video.fragment_video.contract.ContractVideo;

import java.util.List;

public class PresenterVideo extends BasePresenter<ContractVideo.View, ContractVideo.Model> {
    private boolean flag = false;

    public PresenterVideo(ContractVideo.Model mModel, ContractVideo.View mView) {
        super(mModel, mView);
    }

    public void getVideoData(long listTime, String index) {
        mModel.getData(new BaseObserver<Recommend>() {
            @Override
            public void success(Recommend s) {
                List<SqlBean> sqlBeans = DaoManager.getDaoMessage().selectAll();
                for (SqlBean sqlBean : sqlBeans) {
                    String title = sqlBean.getTitle();
                    if (title.equals(index))
                        flag = true;
                }
                if (s.getData().size() != 0)
                    if (flag) {
                        DaoManager.getDaoMessage().change(index, HttpManager.getHttpManager().getGson().toJson(s));
                    } else {
                        DaoManager.getDaoMessage().insert(index, HttpManager.getHttpManager().getGson().toJson(s));
                    }
                mView.getVideoData(s);

            }

            @Override
            public void error(String errorCode, String errorMessage) {

            }


        }, listTime, index);
    }
}
