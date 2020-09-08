package com.example.net.api_srever;

import com.example.net.activity_bean.BaseBean;
import com.example.net.activity_bean.IndexBean;
import com.example.net.activity_bean.InvestBean;
import com.example.net.activity_bean.LoginBean;
import com.example.net.activity_bean.LogoutBean;
import com.example.net.activity_bean.RegisterBean;
import com.example.net.activity_bean.UpdateBean;
import com.example.net.activity_bean.UploadBean;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServer {
    @GET("atguigu/json/P2PInvest/index.json")
    Observable<IndexBean> getIndex();
    @GET("atguigu/json/P2PInvest/update.json")
    Observable<UpdateBean> getVersion();

    @GET("atguigu/json/P2PInvest/product.json")
    Observable<InvestBean> getInvestk();
    @POST("login")
    @FormUrlEncoded
    Observable<LoginBean> loginIn(@FieldMap TreeMap<String,String> params);
    @POST("register")
    @FormUrlEncoded
    Observable<RegisterBean> register(@FieldMap TreeMap<String,String> params);
    @POST("crash")
    @FormUrlEncoded
    Observable<BaseBean<String>> crashReport(@FieldMap HashMap<String,String> params);
    @POST("autoLogin")
    @FormUrlEncoded
    Observable<LoginBean> autoLogin(@Field("token")String token);
    @POST("upload")
    @Multipart
    Observable<UploadBean> uploadFile(@Part("file\";filename=\"hj.jpg") RequestBody requestBody);
    @POST("logout")
    Observable<LogoutBean> logout();
}
