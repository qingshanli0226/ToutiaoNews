package com.example.toutiaonews.welcome;

import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.CacheManager;
import com.example.common.mode.HomeRecommendBean;
import com.example.framework2.base.BaseMVPActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.main.MainActivity;
import com.example.toutiaonews.welcome.contract.RecommendContract;
import com.example.toutiaonews.welcome.presenter.RecommendPresenterImpl;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseMVPActivity<RecommendPresenterImpl, RecommendContract.RecommendView> implements RecommendContract.RecommendView {

    Timer timer;
    int timeCount = 0;
    long currentTime = 0;
    //获取数据的参数数组
    String items[];
    //线程通信的计数
    int handlerCount = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                handlerCount++;
                if(handlerCount == 2){
                    //跳转页面
                    launchActivity(MainActivity.class,null);
                    finish();
                }
            }
        }
    };

    @Override
    protected void initData() {
        //通过资源文件获取数据
        items = getResources().getStringArray(R.array.channel_code);
    }

    @Override
    protected void initView() {
        //隐藏顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //2秒后进入主页面
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(timeCount >= 2){
                    timer.cancel();
                    //发送handler通信 表示倒计时已经完成
                    handler.sendEmptyMessage(1);
                }
                //计数
                timeCount++;
            }
        },0,1000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initHttpData() {
        //从内存中获取recommendBean数据
        HomeRecommendBean homeRecommendBean = CacheManager.getCacheManager().getHomeRecommendBean();
        if(homeRecommendBean == null){
            //没有储存数据 进行网络请求
            currentTime = System.currentTimeMillis();
            //存入请求数据得时间戳
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.LAST_TIME,String.valueOf(currentTime));
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME,String.valueOf(currentTime));
            //获取数据
            iHttpPresenter.getRecommendData(items[0]);

        } else{
            printLog("内存里有数据");
        }

    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new RecommendPresenterImpl();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //手动关闭timer
        timer.cancel();
        //移除handle未处理的消息
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRecommendData(HomeRecommendBean homeRecommendBean) {
        //储存到内存中
        CacheManager.getCacheManager().setHomeRecommendBean(homeRecommendBean);
        //发送handler信号 表示网络请求已经完成
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
