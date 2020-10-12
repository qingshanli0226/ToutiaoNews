package com.example.toutiaonews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.example.common.EmptyViewController;
import com.example.common.custom.CustomToolbar;
import com.example.toutiaonews.emoji.EmojiAdapter;
import com.example.toutiaonews.emoji.FileUtil;
import com.example.toutiaonews.emoji.JsonParseUtil;
import com.example.toutiaonews.login.LoginActivity;


public class NewsDataActivity extends AppCompatActivity {
    private CustomToolbar toolbar;
    private WebView newsWebview;
    private String url;
    private String title;
    private int lastScrollY;
    private int startScrollY;
    private int effectiveScrollY = 10;
    private ScrollView newsScroll;
    private EditText newsComment;
    private ImageView newsCount;
    private ImageView newsLove;
    private ImageView newsShare;
    private EditText spi;

    private Button commentSend;
    private CheckBox popCheck;
    private ImageView popIma;
    private ImageView popAite;
    private ImageView popTag;
    private ImageView popFace;

    private boolean isLogin;

    @Override
    protected void onStart() {
        super.onStart();
        isLogin = SPUtils.getInstance().getBoolean("islogin");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_data);
        toolbar = findViewById(R.id.news_toolbar);
        newsWebview = findViewById(R.id.news_webview);
        newsScroll = findViewById(R.id.news_scroll);
        newsComment = (EditText) findViewById(R.id.news_comment);
        newsCount = (ImageView) findViewById(R.id.news_count);
        newsLove = (ImageView) findViewById(R.id.news_love);
        newsShare = (ImageView) findViewById(R.id.news_share);
        Intent intent = getIntent();
        url = intent.getStringExtra("web");
        title=intent.getStringExtra("title");


        //判断网络是否连接或者可用
        if (NetworkUtils.isConnected()&&NetworkUtils.isAvailableByPing()){
            if (url!=null){
                newsWebview.loadUrl(title);
                newsWebview.setWebViewClient(new WebViewClient());
            }else {
                new EmptyViewController(this,newsScroll).showEmptyView("数据加载失败");
            }
        }else {
            new EmptyViewController(this,newsScroll).showEmptyView("网络出错");
        }

//        newsWebview.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);
//                return true;
//    }
//});
        //声明WebSettings子类
        WebSettings webSettings = newsWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        initData();
}

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initData() {
        ImageView back = toolbar.findViewById(R.id.toolbar_back);
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        //关闭详情页
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //滑动设置标题
        newsScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    if (i3>500){
                    title.setText(title+"");
                }else {
                    title.setText("");
                }
            }
    });
       newsComment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            initpop();
        }
    });
    }

    private void initpop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_comment, null, false);
        PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(toolbar, Gravity.BOTTOM, -150, -1850);
        spi = inflate.findViewById(R.id.spi);
        commentSend = inflate.findViewById(R.id.comment_send);
        popCheck = inflate.findViewById(R.id.pop_check);
        popIma = inflate.findViewById(R.id.pop_ima);
        popAite = inflate.findViewById(R.id.pop_aite);
        popTag = inflate.findViewById(R.id.pop_tag);
        popFace = inflate.findViewById(R.id.pop_face);
        RecyclerView rvEmoji = inflate.findViewById(R.id.rv_emoji);
        //判断登录状态
        if (isLogin){
            commentSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(NewsDataActivity.this, "可以发表", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            //发布按钮
            commentSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(NewsDataActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        //插入图片
        popIma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewsDataActivity.this, "暂不支持插入图片功能", Toast.LENGTH_SHORT).show();
            }
        });
        //选择标签
        popTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View inflate = LayoutInflater.from(NewsDataActivity.this).inflate(R.layout.pop_tag, null, false);
                PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.MATCH_PARENT, true);
                popupWindow.showAtLocation(toolbar, Gravity.BOTTOM, 0, 0);
                CustomToolbar toolbar = inflate.findViewById(R.id.tag_toolbar);
                EditText tagEditText = inflate.findViewById(R.id.tag_ed);
                RecyclerView tagRecyclerView = inflate.findViewById(R.id.tag_rv);
                ImageView tagBack = toolbar.findViewById(R.id.toolbar_back);
                TextView tagTitle = toolbar.findViewById(R.id.toolbar_title);
                tagBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //关闭该popupWindow
                        popupWindow.dismiss();
                    }
                });
                //设置标题
                tagTitle.setText("选择话题");
                tagTitle.setTextSize(1.6f);

            }
        });
        //添加表情
        popFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示表情
                rvEmoji.setVisibility(View.VISIBLE);
                rvEmoji.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.VERTICAL));
                rvEmoji.setAdapter(new EmojiAdapter(JsonParseUtil.parseEmojiList(FileUtil.readAssetsFile(NewsDataActivity.this, "Emoji.json"))));
                //设置点击事件 将表情发送至EditText
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isLogin = SPUtils.getInstance().getBoolean("islogin");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清除当前webview访问的历史记录
        newsWebview.clearHistory();
        //清除缓存
        newsWebview.clearCache(true);
    }
}