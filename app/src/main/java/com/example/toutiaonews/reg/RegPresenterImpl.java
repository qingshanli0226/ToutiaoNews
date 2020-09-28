package com.example.toutiaonews.reg;

import android.util.Log;

import com.example.common.util.EncryptUtil;
import com.example.net.activity_bean.RegisterBean;
import com.example.net.http.RetroCreator;
import com.example.net.model.BaseBean;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegPresenterImpl extends RegContract.RegPresenter {
    @Override
    public void reg(String name, String password) {
        RetroCreator.getmApiServer().register(name,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("zcx", "onSubscribe: "+d.toString());
                    }

                    @Override
                    public void onNext(RegisterBean registerBeanBaseBean) {
                        Log.i("zcx", "onNext: "+"1231111111111111111111111111111111111111111111");
                        if (iHttpView!=null) {
                            iHttpView.onReg(registerBeanBaseBean);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        iHttpView.showError("", "");
                        Log.i("zcx", "onError: "+e.toString()+"++++++++ "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("zcx", "onComplete: ");
                    }
                });
    }
}
