package com.example.toutiaonews.login;

import android.util.Log;

import com.example.common.util.EncryptUtil;
import com.example.net.activity_bean.LoginBean;
import com.example.net.http.RetroCreator;
import com.example.net.model.BaseBean;

import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenterImpl extends LoginContract.LoginPresenter {
    @Override
    public void login(String name, String password) {

        TreeMap<String, String> params = new TreeMap<>();
        params.put("name", name);
        params.put("password", password);
        StringBuilder sb = new StringBuilder();
        for(String key:params.keySet()) {
            String value = params.get(key);
            sb.append(key+"="+value+"&");
        }
        sb.append("encrypt=md5");
        //用md5生成签名
        String sign = EncryptUtil.enrypByMd5(sb.toString());
        params.put("sign", sign);

        //第二步对参数的value进行编码加密，防止明文传输
        TreeMap<String,String> encryptedMap = EncryptUtil.encryptParamsValueByBase64(params);

        RetroCreator.getmApiServer().loginIn(encryptedMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<LoginBean> loginBeanBaseBean) {
                        Log.i("zcx", "onNext: ");
                        if (iHttpView!=null) {
                            iHttpView.onLogin(loginBeanBaseBean.getResult());
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
