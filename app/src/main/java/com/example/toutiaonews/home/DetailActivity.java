package com.example.toutiaonews.home;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.framework2.base.BaseActivity;
import com.example.toutiaonews.R;

/**
 * 详情页面
 */
public class DetailActivity extends BaseActivity {

    private ImageView detailHeadBack;
    private ImageView detailHeadImg;
    private TextView detailHeadName;
    private WebView detailWebView;
    private ProgressBar detailProgress;
    private LinearLayout detailHeadLin;
    private ScrollView detailScrollView;
    //加载网址
    String webViewUrl;
    //作者标题
    String webViewTitle;
    //作者头像
    String webViewAvatar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        //加载作者标题作者头像
        detailHeadName.setText(webViewTitle);
        Glide.with(this).load(webViewAvatar).transform(new CircleCrop()).into(detailHeadImg);
        WebSettings settings = detailWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
        settings.setJavaScriptEnabled(true);
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //加载
        detailWebView.loadUrl(webViewUrl);
        detailWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
                return true;
            }
        });

        //显示进度监听
        detailWebView.setWebViewClient(new WebViewClient(){
            //开始
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                detailProgress.setVisibility(View.VISIBLE);
            }
            //结束
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                detailProgress.setVisibility(View.GONE);
            }
        });

        detailWebView.setWebChromeClient(new WebChromeClient(){
            //进度实时改变
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                detailProgress.setProgress(newProgress);
            }
        });

        //scrollView滑动监听
        detailScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY > 700){
                    //显示
                    detailHeadLin.setVisibility(View.VISIBLE);
                } else{
                    //隐藏
                    detailHeadLin.setVisibility(View.GONE);
                }
            }
        });

        //返回点击事件
        detailHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁webView
                if(detailWebView != null){
                    //先让webView加载空的网址
                    detailWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
                    //清楚历史记录 只会webView访问历史记录里的所有记录除了当前访问记录
                    detailWebView.clearHistory();

                    ((ViewGroup)detailWebView.getParent()).removeView(detailWebView);
                    //停止
                    detailWebView.destroy();
                    //赋值为空
                    detailWebView = null;
                }
                //销毁当前页面
                finish();
            }
        });

    }
    //点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && detailWebView.canGoBack()){
            detailWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void initView() {
        //获取传递过了的webView地址 webView作者标题 webView作者头像
        webViewUrl = getIntent().getExtras().getString(TouTiaoNewsConstant.WEBVIEW_URL);
        webViewTitle = getIntent().getExtras().getString(TouTiaoNewsConstant.WEBVIEW_TITLE);
        webViewAvatar = getIntent().getExtras().getString(TouTiaoNewsConstant.WEBVIEW_AVATAR);
        detailHeadBack = (ImageView) findViewById(R.id.detailHeadBack);
        detailHeadImg = (ImageView) findViewById(R.id.detailHeadImg);
        detailHeadName = (TextView) findViewById(R.id.detailHeadName);
        detailWebView = (WebView) findViewById(R.id.detailWebView);
        detailProgress = (ProgressBar) findViewById(R.id.detailProgress);
        detailHeadLin = (LinearLayout) findViewById(R.id.detailHeadLin);
        detailScrollView = (ScrollView) findViewById(R.id.detailScrollView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁webView
        if(detailWebView != null){
            //先让webView加载空的网址
            detailWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            //清楚历史记录 只会webView访问历史记录里的所有记录除了当前访问记录
            detailWebView.clearHistory();

            ((ViewGroup)detailWebView.getParent()).removeView(detailWebView);
            //停止
            detailWebView.destroy();
            //赋值为空
            detailWebView = null;
        }
        //销毁当前页面
        finish();
    }
}
