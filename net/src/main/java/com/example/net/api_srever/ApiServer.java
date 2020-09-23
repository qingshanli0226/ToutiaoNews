package com.example.net.api_srever;

import com.example.net.activity_bean.LoginBean;
import com.example.net.activity_bean.NewsListBean;
import com.example.net.activity_bean.RegisterBean;
import com.example.net.model.BaseBean;

import java.util.HashMap;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServer {
//    @GET("atguigu/json/P2PInvest/index.json")
//    Observable<IndexBean> getIndex();
//    @GET("atguigu/json/P2PInvest/update.json")
//    Observable<UpdateBean> getVersion();
//
//    @GET("atguigu/json/P2PInvest/product.json")
//    Observable<InvestBean> getInvestk();
    @POST("login")
    @FormUrlEncoded
    Observable<BaseBean<LoginBean>> loginIn(@FieldMap TreeMap<String,String> params);
    @POST("register")
    @FormUrlEncoded
    Observable<BaseBean<RegisterBean>> register(@FieldMap TreeMap<String,String> params);
//    Observable<RegisterBean> register(@Field("name") String name,@Field("password") String pwd);
//    @POST("crash")
//    @FormUrlEncoded
//    Observable<BaseBean<String>> crashReport(@FieldMap HashMap<String,String> params);
//    @POST("autoLogin")
//    @FormUrlEncoded
//    Observable<LoginBean> autoLogin(@Field("token")String token);
//    @POST("upload")
//    @Multipart
//    Observable<UploadBean> uploadFile(@Part("file\";filename=\"hj.jpg") RequestBody requestBody);
//    @POST("logout")
//    Observable<LogoutBean> logout();

    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";
    String GET_COMMENT_LIST = "article/v2/tab_comments/";

    @GET(GET_ARTICLE_LIST)
    Observable<NewsListBean> getNewsList();
}
