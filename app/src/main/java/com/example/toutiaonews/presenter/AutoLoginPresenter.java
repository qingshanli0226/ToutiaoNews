package com.example.toutiaonews.presenter;

import com.example.toutiaonews.bean.AutoLoginEntity;
import com.example.toutiaonews.contract.AutoLoginContract;
import com.example.toutiaonews.net.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AutoLoginPresenter extends AutoLoginContract.AutoLoginPresenter {
    @Override
    public void getAutoLoginData(String token) {
        RetrofitManager.getNewsApi().getAutoLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AutoLoginEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AutoLoginEntity autoLoginEntity) {
                        iHttpView.onAutoLoginData(autoLoginEntity);
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
