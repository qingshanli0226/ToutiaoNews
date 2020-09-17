package com.example.user.presenter;

import com.example.common.mode.LoginOutBean;
import com.example.net.RetroCreator;
import com.example.user.contract.LoginOutContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginOutPresenterImpl extends LoginOutContract.LoginOutPresenter {
    @Override
    public void loginOut(String urlString) {
        RetroCreator.getInvestApiService()
                .loginOut(urlString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginOutBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginOutBean loginOutBean) {
                        if(iHttpView != null){
                            if(loginOutBean.getCode().equals("200")){
                                iHttpView.onLoginOutData(loginOutBean);
                            } else{
                                iHttpView.showError(loginOutBean.getCode(),loginOutBean.getMessage());
                            }
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
