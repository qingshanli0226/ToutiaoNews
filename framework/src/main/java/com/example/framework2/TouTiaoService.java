package com.example.framework2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.common.mode.LoginBean;
import com.example.framework2.manager.UserManager;
import com.example.net.RetroCreator;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class TouTiaoService extends Service {

    public class TouTiaoBinder extends Binder{
        public TouTiaoService getService(){
            return TouTiaoService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new TouTiaoBinder();
    }

    public void autoLogin(String urlString){
        RetroCreator.getInvestApiService()
                .autoLogin(urlString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if(loginBean.getCode().equals("200")){
                            //自动登录成功 赋值
                            UserManager.getInstance().setLoginBean(loginBean);
                            Log.i("123123", "onNext: 自动登录成功");
                        } else{
                            Log.i("123123", "onNext: 自动登录失败");
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
