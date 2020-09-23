package com.example.net.api_srever;


import com.example.common.NetCommon;
import com.example.net.bean.Recommend;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 *
 */
public interface ApiServer {

    @GET(NetCommon.GET_ARTICLE_LIST)
    Observable<Recommend> getVideo(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);


}
