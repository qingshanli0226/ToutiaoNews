package com.example.common.mine;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.viewpager.widget.ViewPager;

import com.example.common.R;


public class BGRefrushLayout extends LinearLayout {
    private LinearLayout linearLayout;
    private ImageView refrustImage;
    private ImageView refrustAnim;
    private TextView refrustText;
    private int headerViewHeight;
    private int paddingTop;
    private int oldY;
    private AnimationDrawable animationDrawable;
    private RecyclerView recyclerView;
    private float lastY;
    private int firstVisiblePosition;
    private IRefreshListener iRefreshListener;
    public BGRefrushLayout(Context context) {
        super(context);
    }

    public BGRefrushLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
    }

    public BGRefrushLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initHeaderView(){
        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        addView(linearLayout);


        View headView = LayoutInflater.from(getContext()).inflate(R.layout.view_refrushlayout, null);
        refrustImage = headView.findViewById(R.id.refrush_image);
        refrustText = headView.findViewById(R.id.refrush_text);
        refrustAnim = headView.findViewById(R.id.refrush_anim);
        animationDrawable = (AnimationDrawable) headView.findViewById(R.id.refrush_anim).getBackground();
        linearLayout.addView(headView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,170));
        headerViewHeight = 170;

        paddingTop = - headerViewHeight;
        linearLayout.setPadding(0, paddingTop, 0, 0);
        headView.bringToFront();
    }

    public void setposition(int position){
        firstVisiblePosition = position;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = (int) ev.getY();
                lastY = ev.getY();//记录当前点击的位置在Y轴的坐标
                break;
            case MotionEvent.ACTION_MOVE:
                if (ev.getY() > oldY && firstVisiblePosition == 0) {
                    return true;//如果等于0就拦截事件，显示下拉刷新的headerview
                } else {
                    return false;
                }
        }
        return false;
    }
    public void attchRecylerView(RecyclerView view){
        recyclerView = view;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int newPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();//获取新滑动到的Item对应的位置
                    firstVisiblePosition = newPosition;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = event.getY();//记录当前点击的位置在Y轴的坐标
                break;
            case MotionEvent.ACTION_MOVE:
                if(event.getY() > lastY && (event.getY() - lastY) <= headerViewHeight){
                    float v = event.getY() - lastY;
                    float pTop = paddingTop + v;
                    linearLayout.setPadding(0, (int) pTop,0,0);//改变头部的top的值，逐渐让它显示出来
                    initAnim(event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getY() - lastY >= headerViewHeight / 2) {//当你滑动的距离超过了headerview高度的一半，就会将headerView显示出来
                    linearLayout.setPadding(0,0,0,0);
                    initAnimLoad();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //两秒钟之后再次隐藏
                            linearLayout.setPadding(0,paddingTop,0,0);
                            animationDrawable.stop();
                            afreshStart();
                            if(iRefreshListener != null){
                                iRefreshListener.onRefreshComplete();
                            }
                        }
                    },2000);
                } else {
                    linearLayout.setPadding(0,paddingTop,0,0);
                }
                break;
        }
        return true;
    }

    private void afreshStart(){
        refrustImage.setVisibility(VISIBLE);
        refrustAnim.setVisibility(GONE);
        refrustText.setText("下拉加载");
    }

    private void initAnimLoad() {
        refrustImage.setVisibility(GONE);
        refrustAnim.setVisibility(VISIBLE);
        refrustText.setText("正在加载");
        new Thread(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        }).start();
    }

    private ObjectAnimator rotationY;
    private void initAnim(float eventY) {
        if(eventY - lastY >= headerViewHeight / 2){
            refrustText.setText("松开刷新");
            rotationY = ObjectAnimator.ofFloat(refrustImage, "rotationX", 0, 180);
            rotationY.setDuration(0);
            rotationY.setRepeatCount(1);
            rotationY.start();
        }else{
            if(rotationY != null){
                refrustText.setText("下拉刷新");
                rotationY = ObjectAnimator.ofFloat(refrustImage, "rotationX", 180, 0);
                rotationY.setDuration(0);
                rotationY.setRepeatCount(1);
                rotationY.start();
            }else{
                return;
            }
        }
    }

    public void addRefreshListener(IRefreshListener iRefreshListener) {
        this.iRefreshListener = iRefreshListener;
    }


    public interface IRefreshListener{
        void onRefreshComplete();
    }

    public void cancel(){
        rotationY.cancel();
    }
}
