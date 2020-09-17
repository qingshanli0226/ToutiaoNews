package com.bw.homemodule.home.presenter;


import com.bw.homemodule.home.contract.HomeContract;
import com.example.common.entity.News;
import com.example.common.entity.NewsData;
import com.example.common.response.NewsResponse;
import com.example.net.retrofit.RetrofitManager;
import com.google.gson.Gson;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImpl extends HomeContract.HomePresenter {

    @Override
    public void getHomeData(String category, long lastTime) {
        RetrofitManager.getNewsApi()
                .getNewsList(category,lastTime/1000,System.currentTimeMillis()/1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsResponse newsResponse) {
                        ArrayList<News> newsList = new ArrayList<>();
                        newsList.clear();
                        if (newsResponse.data.size()!=0){
                            for (NewsData data:newsResponse.data){  //json串读取填list
                                News news = new Gson().fromJson(data.content, News.class);
                                newsList.add(news);
                            }
                        }

                        if (newsList.size()!=0){  //判断有数据时再返回给view层
                            iHttpView.onHomeData(newsList);
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
