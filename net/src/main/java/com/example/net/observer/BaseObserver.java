package com.example.net.observer;

import android.util.Log;
import com.example.common.NetCommon;
import com.example.net.http.NetBusinessException;
import org.json.JSONException;
import java.net.SocketTimeoutException;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

public abstract class BaseObserver<T> extends DisposableObserver<T> implements IObserver<T> {

    @Override
    public void onNext(T t) {
        success(t);
        Log.e("hqhq", "onNext: " );

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof JSONException) {
            error(NetCommon.JSON_ERROR_CODE, NetCommon.JSON_ERROR_MESSAGE);
        } else if (e instanceof HttpException) {
            error(NetCommon.HTTP_ERROR_CODE, NetCommon.HTTP_ERROR_MESSAGE);
        } else if (e instanceof SocketTimeoutException) {
            error(NetCommon.SOCKET_TIMEOUT_ERROR_CODE, NetCommon.SOCKET_TIMEOUT_ERROR_MESSAGE);
        } else if (e instanceof NetBusinessException) {
            NetBusinessException netBusinessException = (NetBusinessException)e;
            error(netBusinessException.getCode(), netBusinessException.getMessage());
        } else if (e instanceof SecurityException) {
            Log.e("hqhq", "onError: "+e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        Log.e("hqhq", "onComplete: " );

    }
}
