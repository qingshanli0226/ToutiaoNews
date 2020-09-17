package com.example.net;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络框架
 */
public class RetroCreator {

    private static ApiService apiService;
    private static final int TIME_SECONDS = 60;

    public static ApiService getInvestApiService() {
        if (apiService == null) {
            apiService = create();
        }
        return apiService;
    }
    //
    private static ApiService create() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIME_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIME_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new TouTiaoHeaderInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

}
