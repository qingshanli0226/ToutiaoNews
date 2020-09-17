package com.example.toutiaonews.fragment_mine;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.ARouterCommon;
import com.example.common.UserCommon;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.MainActivity;
import com.example.toutiaonews.R;
import com.example.user.activity.LoginActivity;
import com.example.user.dao.*;

import static android.app.Activity.RESULT_OK;

public class FragmentMine extends BaseFragment {
    private ImageView ivMineHead;
    private TextView tvMineName;
    private TextView tvMineToMessage;
    private LinearLayout llMineAction;
    private LinearLayout llMineFans;
    private LinearLayout llMineSeven;
    private TextView tvMineCollect;
    private TextView tvMineHistory;
    private TextView tvMineNight;
    private RelativeLayout rlMineInform;
    private RelativeLayout rlMineStore;
    private RelativeLayout rlMineJD;
    private RelativeLayout rlMineDisclose;
    private RelativeLayout rlMineFeedback;
    private RelativeLayout rlMineSetting;
    private TextView tvMineActionNumId;
    private TextView tvMineFansNumId;
    private TextView tvMineSevenNumId;
    private UserBeanDao userBeanDao;
    private String loginYON = "登录/注册";

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_mine_head:
                toCutHead();
                break;
            case R.id.tv_mine_name:
                toLogin();
                break;
            case R.id.ll_mine_action:
                toAction();
                break;
            case R.id.ll_mine_fans:
                toFans();
                break;
            case R.id.ll_mine_seven:
                toSeven();
                break;
            case R.id.tv_mine_collect:
                toCollect();
                break;
            case R.id.tv_mine_history:
                toHistory();
                break;
            case R.id.tv_mine_night:
                setDayNight();
                break;
            case R.id.rl_mine_inform:
                toInform();
                break;
            case R.id.rl_mine_store:
                toStore();
                break;
            case R.id.rl_mine_JD:
                toJDong();
                break;
            case R.id.rl_mine_disclose:
                toDisclose();
                break;
            case R.id.rl_mine_feedback:
                toFeedback();
                break;
            case R.id.rl_mine_setting:
                toSetting();
                break;
        }
    }

    private void toCutHead() {
        if (UserCommon.isLogin){
            Glide.with(this).load(R.mipmap.my_avatar)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivMineHead);
        }else {
            Toast.makeText(getContext(), "未登录，不能切换头像", Toast.LENGTH_SHORT).show();
        }
    }

    private void toSetting() {
        ARouter.getInstance().build(ARouterCommon.USER_SETTING).navigation();
    }

    private void toFeedback() {
        ARouter.getInstance().build(ARouterCommon.USER_FEEDBANCK).navigation();
    }

    private void toDisclose() {
        ARouter.getInstance().build(ARouterCommon.USER_DISCLOSE).navigation();
    }

    private void toJDong() {
        ARouter.getInstance().build(ARouterCommon.USER_JDONG).navigation();
    }

    private void toStore() {
        ARouter.getInstance().build(ARouterCommon.USER_STORE).navigation();
    }

    private void toInform() {
        ARouter.getInstance().build(ARouterCommon.USER_INFORM).navigation();
    }

    private void toHistory() {
        ARouter.getInstance().build(ARouterCommon.USER_HISTORY).navigation();
    }

    private void toCollect() {
        ARouter.getInstance().build(ARouterCommon.USER_COLLECT).navigation();
    }

    private void toSeven() {
        ARouter.getInstance().build(ARouterCommon.USER_SEVEN).navigation();
    }

    private void toFans() {
        ARouter.getInstance().build(ARouterCommon.USER_FANS).navigation();
    }

    private void toAction() {
        ARouter.getInstance().build(ARouterCommon.USER_ACTION).navigation();
    }

    //切换黑暗模式
    private void setDayNight() {
        MainActivity activity = (MainActivity) getActivity();
        if (UserCommon.isDay){
            activity.setNight();
//            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(getContext(), "切换夜间模式", Toast.LENGTH_SHORT).show();
            UserCommon.isDay = false;
        }else {
            activity.setDay();
//            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(getContext(), "切换白天模式", Toast.LENGTH_SHORT).show();
            UserCommon.isDay = true;
        }
    }

    //登录
    private void toLogin() {
        if (tvMineName.getText().equals(loginYON)){
            Toast.makeText(getContext(), "如需登录，请长按跳转至登录界面", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "已登录，长按将重新登录", Toast.LENGTH_SHORT).show();
        }
    }

    //初始化空间以及数据
    @Override
    public void initView() {
        ivMineHead = (ImageView) findViewById(R.id.iv_mine_head);
        tvMineName = (TextView) findViewById(R.id.tv_mine_name);
        tvMineToMessage = (TextView) findViewById(R.id.tv_mine_toMessage);
        llMineAction = (LinearLayout) findViewById(R.id.ll_mine_action);
        llMineFans = (LinearLayout) findViewById(R.id.ll_mine_fans);
        llMineSeven = (LinearLayout) findViewById(R.id.ll_mine_seven);
        tvMineCollect = (TextView) findViewById(R.id.tv_mine_collect);
        tvMineHistory = (TextView) findViewById(R.id.tv_mine_history);
        tvMineNight = (TextView) findViewById(R.id.tv_mine_night);
        rlMineInform = (RelativeLayout) findViewById(R.id.rl_mine_inform);
        rlMineStore = (RelativeLayout) findViewById(R.id.rl_mine_store);
        rlMineJD = (RelativeLayout) findViewById(R.id.rl_mine_JD);
        rlMineDisclose = (RelativeLayout) findViewById(R.id.rl_mine_disclose);
        rlMineFeedback = (RelativeLayout) findViewById(R.id.rl_mine_feedback);
        rlMineSetting = (RelativeLayout) findViewById(R.id.rl_mine_setting);
        tvMineActionNumId = (TextView) findViewById(R.id.tv_mine_action_num_id);
        tvMineFansNumId = (TextView) findViewById(R.id.tv_mine_fans_num_id);
        tvMineSevenNumId = (TextView) findViewById(R.id.tv_mine_seven_num_id);

        ivMineHead.setOnClickListener(this);
        tvMineName.setOnClickListener(this);
        tvMineToMessage.setOnClickListener(this);
        llMineAction.setOnClickListener(this);
        llMineFans.setOnClickListener(this);
        llMineSeven.setOnClickListener(this);
        tvMineCollect.setOnClickListener(this);
        tvMineHistory.setOnClickListener(this);
        tvMineNight.setOnClickListener(this);
        rlMineInform.setOnClickListener(this);
        rlMineStore.setOnClickListener(this);
        rlMineJD.setOnClickListener(this);
        rlMineDisclose.setOnClickListener(this);
        rlMineFeedback.setOnClickListener(this);
        rlMineSetting.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Glide.with(this).load(R.mipmap.ic_launcher)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(ivMineHead);

        tvMineName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("ljl", "clickRegister: 111");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Log.d("ljl", "clickRegister: 222");
                startActivityForResult(intent,101);
                Log.d("ljl", "clickRegister: 333");
                return true;
            }
        });
    }

    @Override
    public void initPresenter() {

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null && resultCode == RESULT_OK && requestCode == 101){
            //是获取注册界面回传过来的用户名
            String userName = data.getStringExtra("username");
            String actionNum = data.getStringExtra("actionNum");
            String fansNum = data.getStringExtra("fansNum");
            String sevenNum = data.getStringExtra("sevenNum");

            Log.d("", "onActivityResult: "+userName);
            Log.d("", "onActivityResult: "+actionNum);
            Log.d("", "onActivityResult: "+fansNum);
            Log.d("", "onActivityResult: "+sevenNum);
            tvMineName.setText(userName+"");
            tvMineActionNumId.setText(actionNum+"");
            tvMineFansNumId.setText(fansNum+"");
            tvMineSevenNumId.setText(sevenNum+"");

            UserCommon.isLogin = true;

            Glide.with(this).load(R.mipmap.my_avatar)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(ivMineHead);

            Log.d("ljl", "onActivityResult: 完成切换");
        }
    }

    //页面布局
    @Override
    public int bandLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserCommon.isLogin = false;
        Log.d("ljl", "onDestroy: 销毁，切换到最初状态");
    }
}
