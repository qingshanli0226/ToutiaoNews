package com.example.toutiaonews.net;

import com.example.common.entity.NewsDetail;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoModel;
import com.example.common.response.CommentResponse;
import com.example.common.response.NewsResponse;
import com.example.common.response.ResultResponse;
import com.example.toutiaonews.bean.AutoLoginEntity;
import com.example.toutiaonews.bean.LoginEntity;
import com.example.toutiaonews.bean.RegisterEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface NewsApi {
    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";
    String GET_COMMENT_LIST = "article/v2/tab_comments/";
    //http://is.snssdk.com
    //http://is.snssdk.com/api/news/feed/v54/?refer=1&count=20&min_behot_time=1498722625&last_refresh_sub_entrance_interval=1498724693&loc_mode=4&tt_from=pull（tab_tip） 新闻列表
    //http://is.snssdk.com/article/v2/tab_comments/?group_id=6436886053704958466&item_id=6436886053704958466&offset=30&count=20 评论
    //http://is.snssdk.com/2/article/information/v21/ 详情
    @FormUrlEncoded
    @POST("autoLogin")
    Observable<AutoLoginEntity> getAutoLogin(@Field("token") String token);
    @FormUrlEncoded
    @POST("register")
    Observable<RegisterEntity> getRegister(@Field("name") String name,@Field("password") String password);
    @FormUrlEncoded
    @POST("login")
    Observable<LoginEntity> getLogin(@Field("name") String name, @Field("password") String password);

}
