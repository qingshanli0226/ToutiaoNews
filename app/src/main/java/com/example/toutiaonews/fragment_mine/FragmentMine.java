package com.example.toutiaonews.fragment_mine;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.MainActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.Share;
import com.example.toutiaonews.fragment_mine.activity.Action1Activity;
import com.example.toutiaonews.fragment_mine.activity.CollectActivity;
import com.example.toutiaonews.fragment_mine.activity.DiscloseActivity;
import com.example.toutiaonews.fragment_mine.activity.FansActivity;
import com.example.toutiaonews.fragment_mine.activity.FeedbackActivity;
import com.example.toutiaonews.fragment_mine.activity.HistoryActivity;
import com.example.toutiaonews.fragment_mine.activity.InformActivity;
import com.example.toutiaonews.fragment_mine.activity.JDongActivity;
import com.example.toutiaonews.fragment_mine.activity.SettingActivity;
import com.example.toutiaonews.fragment_mine.activity.SevenActivity;
import com.example.toutiaonews.fragment_mine.activity.StoreActivity;

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
    private boolean isDay = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_mine_head:
                login();
                break;
            case R.id.tv_mine_name:
                login();
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

    private void toSetting() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    private void toFeedback() {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        startActivity(intent);
    }

    private void toDisclose() {
        Intent intent = new Intent(getActivity(), DiscloseActivity.class);
        startActivity(intent);
    }

    private void toJDong() {
        Intent intent = new Intent(getActivity(), JDongActivity.class);
        startActivity(intent);
    }

    private void toStore() {
        Intent intent = new Intent(getActivity(), StoreActivity.class);
        startActivity(intent);
    }

    private void toInform() {
        Intent intent = new Intent(getActivity(), InformActivity.class);
        startActivity(intent);
    }

    private void toHistory() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        startActivity(intent);
    }

    private void toCollect() {
        Intent intent = new Intent(getActivity(), CollectActivity.class);
        startActivity(intent);
    }

    private void toSeven() {
        Intent intent = new Intent(getActivity(), SevenActivity.class);
        startActivity(intent);
    }

    private void toFans() {
        Intent intent = new Intent(getActivity(), FansActivity.class);
        startActivity(intent);
    }

    private void toAction() {
        Intent intent = new Intent(getActivity(), Action1Activity.class);
        startActivity(intent);
    }

    //切换黑暗模式
    private void setDayNight() {
        MainActivity activity = (MainActivity) getActivity();
        if (Share.isDay){
            activity.setNight();
//            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(getContext(), "切换夜间模式", Toast.LENGTH_SHORT).show();
            Share.isDay = false;
        }else {
            activity.setDay();
//            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(getContext(), "切换白天模式", Toast.LENGTH_SHORT).show();
            Share.isDay = true;
        }
    }

    //登录
    private void login() {
        Toast.makeText(getContext(), "头像", Toast.LENGTH_SHORT).show();
    }

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
        Glide.with(this).load(R.mipmap.my_avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(ivMineHead);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_mine;
    }
}
