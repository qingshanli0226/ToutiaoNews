package com.example.toutiaonews.service;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import com.example.toutiaonews.appcontract.TouTiaoAppLication;
import com.example.toutiaonews.bean.AutoLoginEntity;
import com.example.toutiaonews.net.RetrofitManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
public class TouTiaoIntentService extends Service {
    private TouTiaoAppLication application;
    public class TouTiaoBinder extends Binder {
        public TouTiaoIntentService getService(){
        return TouTiaoIntentService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return new TouTiaoBinder();
    }

    public void autoLogin(String tokenstring){
        RetrofitManager.getNewsApi().getAutoLogin(tokenstring)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AutoLoginEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AutoLoginEntity autoLoginEntity) {
                        if (autoLoginEntity.getCode().equals("200")){
                            application.tofLogin=true;
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

//
//    private void getonautoLogin() {
//        //自动登录网络请求
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("token",token);
//        FormBody body = builder.build();
//        OkGo.<String>post("http://49.233.93.155:8080/autoLogin")
//                .upRequestBody(body)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String json = response.body();
//                        Gson gson = new Gson();
//                        AutoLoginEntity autoLoginEntity = gson.fromJson(json, AutoLoginEntity.class);
//                        if (autoLoginEntity.getCode().equals("200")){
//                            Toast.makeText(TouTiaoIntentService.this, autoLoginEntity.getMessage(), Toast.LENGTH_SHORT).show();
//                            application.tofLogin=true;
//                        }
//                    }
//                });
//    }


}
