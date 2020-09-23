package com.example.toutiaonews.reg;

import android.util.Log;

import com.example.common.util.EncryptUtil;
import com.example.net.activity_bean.RegisterBean;
import com.example.net.http.RetroCreator;
import com.example.net.model.BaseBean;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegPresenterImpl extends RegContract.RegPresenter {
    @Override
    public void reg(String name, String password) {
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
        RetroCreator.getmApiServer().register(encryptedMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean<RegisterBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("zcx", "onSubscribe: "+d.toString());
                    }

                    @Override
                    public void onNext(BaseBean<RegisterBean> registerBeanBaseBean) {
                        Log.i("zcx", "onNext: "+"1231111111111111111111111111111111111111111111");
                        if (iHttpView!=null) {
                            iHttpView.onReg(registerBeanBaseBean.getResult());
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
