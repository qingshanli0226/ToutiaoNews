package com.example.toutiaonews.welcome;

import android.view.WindowManager;

import com.example.common.manager.CacheManager;
import com.example.framework2.base.BaseMVPActivity;
import com.example.common.mode.RecommendBean;
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
                    //跳转页面
                    launchActivity(MainActivity.class,null);
                    finish();
                    timer.cancel();
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
        RecommendBean recommendBean = CacheManager.getCacheManager().getRecommendBean();
        if(recommendBean == null){
            //没有储存数据 进行网络请求
            currentTime = System.currentTimeMillis();
            //获取数据
            iHttpPresenter.getRecommendData(items[0],currentTime);
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
    }

    @Override
    public void onRecommendData(RecommendBean recommendBean) {
        //储存到内存中
        CacheManager.getCacheManager().setRecommendBean(recommendBean);
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
