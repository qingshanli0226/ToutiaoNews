package com.example.toutiaonews.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static com.example.toutiaonews.net.NewsApi newsApi;
    public static NewsApi getNewsApi(){
        if(newsApi == null){
            newsApi = createNews();
        }
        return newsApi;
    }

    private static com.example.toutiaonews.net.NewsApi createNews() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60*1000, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000, TimeUnit.MILLISECONDS)
                .writeTimeout(60*1000, TimeUnit.MILLISECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://49.233.93.155:8080/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(com.example.toutiaonews.net.NewsApi.class);
    }
}
