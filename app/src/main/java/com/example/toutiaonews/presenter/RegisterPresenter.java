package com.example.toutiaonews.presenter;

import com.example.toutiaonews.bean.RegisterEntity;
import com.example.toutiaonews.contract.RegisterContract;
import com.example.toutiaonews.net.RetrofitManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegisterPresenter extends RegisterContract.registerPresenter {
    @Override
    public void getRegisterData(String name, String password) {
        RetrofitManager.getNewsApi().getRegister(name,password)
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterEntity registerEntity) {
                        iHttpView.onRegisterData(registerEntity);
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
