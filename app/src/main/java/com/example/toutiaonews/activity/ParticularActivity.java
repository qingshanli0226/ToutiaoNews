package com.example.toutiaonews.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.expression.EmojiAdapter;
import com.example.toutiaonews.expression.EmojiEntity;
import com.example.toutiaonews.expression.FileUtil;
import com.example.toutiaonews.expression.JsonParseUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ParticularActivity extends BaseActivity {
    private ImageView back;
    private ImageView more;
    private WebView web;
    private TextView writeCommentary;
    private ImageView commentary;
    private ImageView collect;
    private ImageView share;
    private UMShareListener shareListener;
    private String htmlurl;
    private String picurl;
    private boolean isCollect=false;
    private boolean isemoji=false;
    @Override
    protected void initData() {
        //获取网页
        String s = "http://toutiao.com/group/6876240504984437256/\\" ;

        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl(s);
        //关掉此要页面
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //更多
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getOnShare();
            }
        });
        //分享
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnShare();
            }
        });
        //收藏
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnCollect();
            }
        });
        //写评论
        writeCommentary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnWriteCommentary();
            }
        });

    }
    //收藏
    private void getOnCollect(){
        //判断是否收藏过
        if (isCollect){
            isCollect=false;
            collect.setImageResource(R.mipmap.my_collect);
        }else {
            isCollect=true;
            collect.setImageResource(R.mipmap.new_love_tabbar);
        }

    }
    //写评论
    private void getOnWriteCommentary(){
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(getWallpaperDesiredMinimumWidth()/2);
        View inflate = getLayoutInflater().inflate(R.layout.popopwindow_writecommentary, null);
        popupWindow.setContentView(inflate);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(writeCommentary,Gravity.BOTTOM,0,0);
        final Button issue = inflate.findViewById(R.id.issue);
        final EditText mes = inflate.findViewById(R.id.mes);
        final RecyclerView recExpression = inflate.findViewById(R.id.recExpression);
//        String emoji = FileUtil.readAssetsFile(this, "Emoji.json");
//        List<EmojiEntity> emojiEntities = JsonParseUtil.parseEmojiList(emoji);
//        EmojiAdapter emojiAdapter = new EmojiAdapter(emojiEntities);
//        recExpression.setAdapter(emojiAdapter);
        final List<EmojiEntity> emojiEntities = JsonParseUtil.parseEmojiList(FileUtil.readAssetsFile(this, "Emoji.json"));
//        EmojiAdapter emojiAdapter = new EmojiAdapter(emojiEntities);
        final EmojiAdapter emojiAdapter = new EmojiAdapter(R.layout.item_emoji, emojiEntities);
        recExpression.setAdapter(emojiAdapter);
        recExpression.setLayoutManager(new StaggeredGridLayoutManager(7,StaggeredGridLayoutManager.HORIZONTAL));

        //表情
        TextView smiling = inflate.findViewById(R.id.smiling);
        smiling.setText(emojiEntities.get(0).getUnicode());
        smiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isemoji){
                    isemoji=false;
                    recExpression.setVisibility(View.GONE);
                }else {
                    isemoji=true;
                    recExpression.setVisibility(View.VISIBLE);
                }

            }
        });
        //点击输入表情
        emojiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String unicode = emojiEntities.get(position).getUnicode();
                Editable text = mes.getText();
                mes.setText(text+unicode);
            }
        });

        //点击发布
        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ParticularActivity.this, "发布", Toast.LENGTH_SHORT).show();
            }
        });


    }
    //分享
    private void getOnShare(){
        UMImage image = new UMImage(ParticularActivity.this, picurl);//网络图片
        new ShareAction(ParticularActivity.this).withMedia(image).withText(htmlurl).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
        shareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
             * @param platform 平台类型
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            /**
             * @descrption 分享成功的回调
             * @param platform 平台类型
             */
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(ParticularActivity.this,"成功了",Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享失败的回调
             * @param platform 平台类型
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(ParticularActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享取消的回调
             * @param platform 平台类型
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(ParticularActivity.this,"取消了",Toast.LENGTH_LONG).show();

            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected int bandLayout() {
        return R.layout.activity_particular;
    }

    @Override
    protected void initView() {
        back = (ImageView) findViewById(R.id.back);
        more = (ImageView) findViewById(R.id.more);
        web = (WebView) findViewById(R.id.web);
        writeCommentary =  findViewById(R.id.writeCommentary);
        commentary = (ImageView) findViewById(R.id.commentary);
        collect = (ImageView) findViewById(R.id.collect);
        share = (ImageView) findViewById(R.id.share);
        //动态申请权限
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        Intent intent = getIntent();
         htmlurl = intent.getStringExtra("htmlurl");
         picurl = intent.getStringExtra("picurl");
    }



}
