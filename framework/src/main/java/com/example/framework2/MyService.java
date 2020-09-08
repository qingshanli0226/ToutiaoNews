package com.example.framework2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.framework2.manager.CacheManager;
import com.example.net.activity_bean.LoginBean;
import com.example.net.activity_bean.UploadBean;
import com.example.net.api_srever.ApiServer;
import com.example.net.http.HttpManager;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    public class MyBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
    public void uploadFile(String path){
        File file = new File(path);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("multipart/form-data"),file);
        HttpManager.getHttpManager().getRetrofit()
                .create(ApiServer.class)
                .uploadFile(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UploadBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UploadBean uploadBean) {
                        if (uploadBean.getCode().equals("200")){
                            Toast.makeText(MyService.this, "上传成功", Toast.LENGTH_SHORT).show();
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
    public void autoLogin(String token){
        HttpManager.getHttpManager()
                .getRetrofit()
                .create(ApiServer.class)
                .autoLogin(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.getCode().equals("200")){
                            CacheManager.getInstance().setLoginBean(loginBean);
                            Toast.makeText(MyService.this, "自动登录成功", Toast.LENGTH_SHORT).show();
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
