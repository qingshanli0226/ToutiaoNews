package com.example.net;


import com.example.common.constant.Constant;

import org.json.JSONException;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserable<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        if (e instanceof JSONException) {
            onRequestError(Constant.JSCON_ERROR_CODE, Constant.JSON_ERROR_MESSAGE);
        } else if (e instanceof HttpException) {
            onRequestError(Constant.HTTP_ERROR_CODE, Constant.HTTP_ERROR_MESSAGE);
        } else if (e instanceof SocketTimeoutException) {
            onRequestError(Constant.SOCKET_TIMEOUT_ERROR_CODE, Constant.SOCKET_TIMEOUT_ERROR_MESSAGE);
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
