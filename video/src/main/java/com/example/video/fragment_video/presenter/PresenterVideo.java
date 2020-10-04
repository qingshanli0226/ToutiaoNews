package com.example.video.fragment_video.presenter;


import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.net.bean.Recommend;
import com.example.net.bean.VideoEntity;
import com.example.net.http.HttpManager;
import com.example.net.observer.BaseObserver;
import com.example.video.bean.SqlBean;
import com.example.video.dao.DaoManager;
import com.example.video.fragment_video.contract.ContractVideo;
import java.util.List;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PresenterVideo extends BasePresenter<ContractVideo.View, ContractVideo.Model> {
    private boolean flag = false;

    public PresenterVideo(ContractVideo.Model mModel, ContractVideo.View mView) {
        super(mModel, mView);
    }


    public void getVideoData(long listTime, String index) {
        mModel.getData(new BaseObserver<VideoEntity>() {
            @Override
            public void success(VideoEntity s) {
                List<SqlBean> sqlBeans = DaoManager.getDaoMessage().selectAll();
                for (SqlBean sqlBean : sqlBeans) {
                    String title = sqlBean.getTitle();
                    if (title.equals(index)){
                        flag = true;
                        break;
                    }

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


        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                mDisposable = disposable;
                mView.hideView();
            }
        }, listTime, index);

    }
}
