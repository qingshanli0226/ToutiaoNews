package com.example.home.model;

import com.example.common.NetCommon;
import com.example.framework2.mvp.model.BaseModel;
import com.example.home.contract.ContractRecommend;
import com.example.net.api_srever.ApiServer;
import com.example.net.bean.Recommend;
import com.example.net.http.HttpManager;
import com.example.net.observer.BaseObserver;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ModelRecommend extends BaseModel implements ContractRecommend.Model {
    @Override
    public void getData(BaseObserver<Recommend> baseObserver, long listTime, String index) {
        long currentTime = System.currentTimeMillis();
        HttpManager.getHttpManager().setPath(NetCommon.BASE_URL);
        HttpManager.getHttpManager()
                .getRetrofit()
                .create(ApiServer.class)
                .getVideo(index, listTime / 1000, currentTime / 1000000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }
}
