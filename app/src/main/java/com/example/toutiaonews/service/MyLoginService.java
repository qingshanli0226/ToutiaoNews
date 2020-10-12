package com.example.toutiaonews.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.net.activity_bean.autoLoginBean;
import com.example.net.http.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyLoginService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyLoginBinder();
    }

    public class MyLoginBinder extends Binder{
        public MyLoginService getService(){
            return MyLoginService.this;
        }
    }

    public void autoLogin(String tokenstring){
        RetroCreator.getmApiServer().getAutoLogin(tokenstring)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<autoLoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(autoLoginBean autoLoginEntity) {
                        if (autoLoginEntity.getCode().equals("200")){

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
