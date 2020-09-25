package com.example.toutiaonews.home;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.framework2.base.BaseActivity;
import com.example.toutiaonews.R;

public class PlayerActivity extends BaseActivity {

    private ProgressBar playerProgress;
    private WebView playerWebView;

    String webViewUrl;

    @Override
    protected void initData() {
        WebSettings settings = playerWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //加载
        playerWebView.loadUrl(webViewUrl);
        playerWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });

        //显示进度监听
        playerWebView.setWebViewClient(new WebViewClient(){
            //开始
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                playerProgress.setVisibility(View.VISIBLE);
            }
            //结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                playerProgress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initView() {
        //获取传递过了的webView地址
        webViewUrl = getIntent().getExtras().getString(TouTiaoNewsConstant.WEBVIEW_URL);
        playerProgress = (ProgressBar) findViewById(R.id.playerProgress);
        playerWebView = (WebView) findViewById(R.id.playerWebView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_player;
    }

    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && playerWebView.canGoBack()){
            playerWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁webView
        if(playerWebView != null){
            //先让webView加载空的网址
            playerWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            //清楚历史记录 只会webView访问历史记录里的所有记录除了当前访问记录
            playerWebView.clearHistory();

            ((ViewGroup)playerWebView.getParent()).removeView(playerWebView);
            //停止
            playerWebView.destroy();
            //赋值为空
            playerWebView = null;
        }
        //销毁当前页面
        finish();
    }
}
