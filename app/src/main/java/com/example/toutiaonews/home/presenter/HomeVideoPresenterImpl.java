package com.example.toutiaonews.home.presenter;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.CacheManager;
import com.example.common.mode.HomeVideoBean;
import com.example.net.RetroCreator;
import com.example.toutiaonews.home.contract.HomeVideoContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeVideoPresenterImpl extends HomeVideoContract.HomeVideoPresenter {

    String lastTime;
    String currentTime;

    @Override
    public void getVideoData(String category) {
        //获取sp文件里的时间戳
        lastTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.LAST_TIME);
        currentTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.CURRENT_TIME);
        //储存时间戳
        CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.LAST_TIME,currentTime);
        RetroCreator.getInvestApiService().getVideoList(category,Long.parseLong(lastTime),Long.parseLong(currentTime))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeVideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeVideoBean homeVideoBean) {
                        iHttpView.onViewData(homeVideoBean);
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
