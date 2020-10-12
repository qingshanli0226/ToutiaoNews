package com.example.videomodule.video.presenter;


import com.example.common.cache.CacheManager;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.net.obserable.BaseObserable;
import com.example.net.retrofit.RetrofitManager;
import com.example.videomodule.video.contract.VideoContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VideoPresenterImpl extends VideoContract.VideoPresenter {
    private long firstTime;
    @Override
    public void getVideoData(String category,String channel) {
        firstTime = CacheManager.getInstance().getFirstTime("first",0);
        if(firstTime != 0){
            firstTime = System.currentTimeMillis() / 1000;
        }
        CacheManager.getInstance().putVisitTime(category, System.currentTimeMillis());
        RetrofitManager.getNewsApi().getVideoList(category,firstTime,System.currentTimeMillis()/1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserable<VideoBean>() {
                    @Override
                    public void onNext(VideoBean videoBean) {
                        //参数时间戳
                        firstTime = System.currentTimeMillis() / 1000;
                        CacheManager.getInstance().putFirstTime("first",firstTime);
                        //请求过网络数据
                        CacheManager.getInstance().putIsVisit(channel, true);
                        if(!videoBean.toString().equals("")){
                            for (int i = 0; i < videoBean.getData().size(); i++) {
                                String content = videoBean.getData().get(i).getContent();
                                NewsRoomBean newsRoomBean = new NewsRoomBean();
                                newsRoomBean.setChannelId(category);
                                newsRoomBean.setJsonUrl(content);
                                newsRoomBean.setNewsTime(System.currentTimeMillis());
                                CacheManager.getInstance().insert(newsRoomBean);
                                iHttpView.onVideoData(content);
                            }
                        }else{
                            iHttpView.showError("0", "接口没有数据,请重新下拉刷新");
                        }
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        iHttpView.showError(errorCode, "请求失败,请重试");
                    }
                });
    }
}