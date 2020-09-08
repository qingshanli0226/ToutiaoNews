package com.example.net.http;

import com.example.net.api_srever.ApiServer;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager httpManager;
    private String path;
    private Retrofit retrofit;
    private Gson gson;

    public ApiServer getRetrofit() {
        return create();
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    private ApiServer create() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new MyInterceptor())
                .connectTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .readTimeout(10, TimeUnit.MINUTES)
                .build();
        ApiServer apiServer = new Retrofit.Builder()
                .client(client)
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiServer.class);

        return apiServer;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static HttpManager getHttpManager() {
        if (httpManager == null) {
            httpManager = new HttpManager();
        }
        return httpManager;
    }

}
