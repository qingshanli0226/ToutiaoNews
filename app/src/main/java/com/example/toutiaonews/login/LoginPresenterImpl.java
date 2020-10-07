package com.example.toutiaonews.login;

import android.util.Log;
import com.example.net.activity_bean.LoginBean;
import com.example.net.http.RetroCreator;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl extends LoginContract.LoginPresenter {
        @Override
        public void login(String name, String password) {

            RetroCreator.getmApiServer().loginIn(name,password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBeanBaseBean) {
                        Log.i("zcx", "onNext: ");
                        if (iHttpView!=null) {
                            iHttpView.onLogin(loginBeanBaseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iHttpView.showError("", "");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
