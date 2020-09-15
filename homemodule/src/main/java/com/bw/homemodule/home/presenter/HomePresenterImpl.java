package com.bw.homemodule.home.presenter;


import com.bw.homemodule.home.contract.HomeContract;
import com.example.common.response.NewsResponse;
import com.example.net.retrofit.RetrofitManager;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl extends HomeContract.HomePresenter {

    @Override
    public void getHomeData(String category) {

        Date date = new Date();
        RetrofitManager.getNewsApi()
                .getNewsList(category,date.getTime(),date.getTime())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        iHttpView.onHomeData(newsResponse);
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
