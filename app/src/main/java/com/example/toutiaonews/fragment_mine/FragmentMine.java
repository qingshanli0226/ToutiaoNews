package com.example.toutiaonews.fragment_mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.common.ARouterCommon;
import com.example.common.UserCommon;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.MainActivity;
import com.example.toutiaonews.R;

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_mine_head:
                toLogin();
                break;
            case R.id.tv_mine_name:
                toRegister();
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

    private void toRegister() {
        if (UserCommon.register_str.equals(tvMineName.getText())){
            ARouter.getInstance().build(ARouterCommon.USER_REGISTER).navigation();
        }else {
            toLogin();
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
        if (UserCommon.isLogin){
            Toast.makeText(getContext(), "头像", Toast.LENGTH_SHORT).show();
            UserCommon.isLogin = false;
        }else {
            Toast.makeText(getContext(), "请登录", Toast.LENGTH_SHORT).show();
            UserCommon.isLogin = true;
        }
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
