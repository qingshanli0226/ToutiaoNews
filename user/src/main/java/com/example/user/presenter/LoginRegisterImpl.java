package com.example.user.presenter;

import com.example.net.RetroCreator;
import com.example.common.mode.LoginBean;
import com.example.common.mode.RegisterBean;
import com.example.user.contract.LoginRegisterContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginRegisterImpl extends LoginRegisterContract.LoginRegisterPresenter {
    @Override
    public void register(String urlString) {
        RetroCreator.getInvestApiService().register(urlString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterBean registerBean) {
                        if(registerBean.getCode().equals(200)){
                            iHttpView.onRegisterData(registerBean);
                        } else{
                            iHttpView.showError(registerBean.getCode(),registerBean.getMessage());
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

    @Override
    public void login(String urlString) {
        RetroCreator.getInvestApiService().login(urlString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if(loginBean.getCode().equals("200")){
                            iHttpView.onLoginData(loginBean);
                        } else{
                            iHttpView.showError(loginBean.getCode(),loginBean.getMessage());
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
