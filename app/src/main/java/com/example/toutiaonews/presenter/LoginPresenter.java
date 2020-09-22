package com.example.toutiaonews.presenter;

import com.example.toutiaonews.bean.LoginEntity;
import com.example.toutiaonews.contract.LoginContract;
import com.example.toutiaonews.net.RetrofitManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends LoginContract.ILoginPresenter {
    @Override
    public void getLoginData(String name, String password) {
        RetrofitManager.getNewsApi().getLogin(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginEntity loginEntity) {
                        iHttpView.onLoginData(loginEntity);
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
