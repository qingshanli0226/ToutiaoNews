package com.example.toutiaonews.welcome.presenter;

import com.example.net.RetroCreator;
import com.example.common.mode.RecommendBean;
import com.example.toutiaonews.welcome.contract.RecommendContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RecommendPresenterImpl extends RecommendContract.RecommendPresenter {

    long lastTime;

    @Override
    public void getRecommendData(String category,long currentTime) {
        lastTime = System.currentTimeMillis();
        RetroCreator.getInvestApiService()
                .getNewsList(category,lastTime,currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RecommendBean recommendBean) {
                        if(recommendBean.getMessage().equals("success")){
                            iHttpView.onRecommendData(recommendBean);
                        } else{
                            iHttpView.showError("404",recommendBean.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
