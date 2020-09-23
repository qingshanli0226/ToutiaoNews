package com.example.net;

import android.util.Log;


import com.example.common.NetCommon;
import com.example.net.http.NetBusinessException;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

//自定义一个observer，让presenter实现类更清晰，干净
public abstract class CustomObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {//该函数可以扩展处理各种类型的错误
        Log.d("zcx error message-----", e.getMessage());
        if (e instanceof JSONException) {
            onRequestError(NetCommon.JSCON_ERROR_CODE, NetCommon.JSON_ERROR_MESSAGE);
        } else if (e instanceof HttpException) {
            onRequestError(NetCommon.HTTP_ERROR_CODE, NetCommon.HTTP_ERROR_MESSAGE);
        } else if (e instanceof SocketTimeoutException) {
            onRequestError(NetCommon.SOCKET_TIMEOUT_ERROR_CODE, NetCommon.SOCKET_TIMEOUT_ERROR_MESSAGE);
        } else if (e instanceof NetBusinessException) {
            NetBusinessException netBusinessException = (NetBusinessException)e;
            onRequestError(netBusinessException.getErrorCode(), netBusinessException.getErrorMessage());
        } else if (e instanceof SecurityException) {

        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onRequestError(String errorCode, String errorMessage);
}
