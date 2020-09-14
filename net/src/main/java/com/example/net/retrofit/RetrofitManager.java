package com.example.net.retrofit;

import com.example.common.constant.ApiConstant;
import com.example.net.api.NewsApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static NewsApi newsApi;
    public static NewsApi getNewsApi(){
        if(newsApi == null){
            newsApi = createNews();
        }
        return newsApi;
    }

    private static NewsApi createNews() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60*1000, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NewsApi.class);
    }
}
