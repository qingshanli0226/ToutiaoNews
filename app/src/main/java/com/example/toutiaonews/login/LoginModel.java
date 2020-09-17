package com.example.toutiaonews.login;

import android.util.Log;

import com.example.common.util.EncryptUtil;
import com.example.framework2.mvp.model.BaseModel;
import com.example.net.activity_bean.LoginBean;
import com.example.net.http.HttpManager;

import java.util.TreeMap;

import io.reactivex.Observable;

public class LoginModel extends BaseModel implements LoginContract.ILoginModel {
    @Override
    public void login(String name,String pwd,Observable<LoginBean> observable) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("name", name);
        params.put("password", pwd);
        StringBuilder sb = new StringBuilder();
        for(String key:params.keySet()) {
            String value = params.get(key);
            sb.append(key+"="+value+"&");
        }
        sb.append("encrypt=md5");
        Log.d("zcx", sb.toString());
        //用md5生成签名
        String sign = EncryptUtil.enrypByMd5(sb.toString());
        params.put("sign", sign);
//       HttpManager.getHttpManager().getRetrofit()
//               .loginIn()
    }
}
