package com.example.net.http;

import com.example.common.NetCommon;
import com.example.net.api_srever.ApiServer;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroCreator  {
    //单例
    private static ApiServer mApiServer;
    public static ApiServer getmApiServer() {
        if (mApiServer == null) {
            mApiServer = createApiServer();
        }

        return mApiServer;
    }

    private static ApiServer createApiServer() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new MyInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.233.93.155:8080/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiServer.class);
    }
}
