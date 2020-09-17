package com.example.net;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TouTiaoHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder().addHeader("token", CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.SP_TOKEN)).build();
        return chain.proceed(build);
    }
}
