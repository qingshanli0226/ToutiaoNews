package com.example.toutiaonews.welcome.presenter;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.CacheManager;
import com.example.net.RetroCreator;
import com.example.common.mode.HomeRecommendBean;
import com.example.toutiaonews.welcome.contract.RecommendContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RecommendPresenterImpl extends RecommendContract.RecommendPresenter {

    String lastTime;
    String currentTime;

    @Override
    public void getRecommendData(String category) {
        //获取是否是隔一段时间后的请求网络数据的状态
        boolean isTwoData = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISTWODATA);
        if(isTwoData){
            //如果是 获取用户可见时的时间戳
            lastTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.LAST_TIME);
            currentTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.USERLOOKTIME);
            //储存时间戳
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.LAST_TIME,currentTime);
        } else{
            //如果不是  就是刷新时候的时间戳
            //获取sp文件里的时间戳
            lastTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.LAST_TIME);
            currentTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.CURRENT_TIME);
            //储存时间戳
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.LAST_TIME,currentTime);
        }
        //请求数据
        RetroCreator.getInvestApiService()
                .getNewsList(category,Long.parseLong(lastTime),Long.parseLong(currentTime))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        gDisposable = disposable;
                    }
                })
                .subscribe(new Observer<HomeRecommendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeRecommendBean homeRecommendBean) {
                            if(homeRecommendBean.getMessage().equals("success")){
                                iHttpView.onRecommendData(homeRecommendBean);
                            } else{
                                iHttpView.showError("404", homeRecommendBean.getMessage());
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
