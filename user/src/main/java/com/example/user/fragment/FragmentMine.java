package com.example.user.fragment;

import android.content.Intent;
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

import com.example.framework2.utils.Users;
import com.example.user.activity.LoginActivity;
import com.example.user.fragment.dialog.MyDialog;
import com.example.video.R;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private String loginYON = "登录/注册";

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_mine_head) {
            toCutHead();
        } else if (id == R.id.tv_mine_name) {
            toLogin();
        } else if (id == R.id.ll_mine_action) {
            toAction();
        } else if (id == R.id.ll_mine_fans) {
            toFans();
        } else if (id == R.id.ll_mine_seven) {
            toSeven();
        } else if (id == R.id.tv_mine_collect) {
            toCollect();
        } else if (id == R.id.tv_mine_history) {
            toHistory();
        } else if (id == R.id.tv_mine_night) {
            setDayNight();
        } else if (id == R.id.rl_mine_inform) {
            toInform();
        } else if (id == R.id.rl_mine_store) {
            toStore();
        } else if (id == R.id.rl_mine_JD) {
            toJDong();
        } else if (id == R.id.rl_mine_disclose) {
            toDisclose();
        } else if (id == R.id.rl_mine_feedback) {
            toFeedback();
        } else if (id == R.id.rl_mine_setting) {
            toSetting();
        }
    }

    //切换头像
    private void toCutHead() {
        PictureSelector.create(FragmentMine.this, PictureSelector.SELECT_REQUEST_CODE).selectPicture();
//        if (UserCommon.isLogin){
//            PictureSelector.create(FragmentMine.this, SELECT_REQUEST_CODE).selectPicture();
//        }else {
//            Toast.makeText(getContext(), "未登录，不能切换头像", Toast.LENGTH_SHORT).show();
//        }
    }

    //跳转设置页面
    private void toSetting() {
        ARouter.getInstance().build(ARouterCommon.USER_SETTING).navigation();
    }

    private void toFeedback() {
        MyDialog myDialog = new MyDialog(getContext(), R.style.MyTheDialog);
        myDialog.show();
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
//        MainActivity activity = (MainActivity) getActivity();
        if (UserCommon.isDay){
//            activity.setNight();
//            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Toast.makeText(getContext(), "切换夜间模式", Toast.LENGTH_SHORT).show();
            UserCommon.isDay = false;
        }else {
//            activity.setDay();
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
        EventBus.getDefault().register(this);

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

        //长按跳转登录
        tvMineName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("ljl", "clickRegister: 111");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                Log.d("ljl", "clickRegister: 222");
                startActivity(intent);
                Log.d("ljl", "clickRegister: 333");
                return true;
            }
        });
    }

    @Override
    public void initPresenter() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(Users user){
        if (user.isLogin()){
            tvMineName.setText(user.getUsername());
            Glide.with(this).load(R.mipmap.icon).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivMineHead);
        }else {

        }
    }

    //接收返回的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE && data != null) {
            PictureBean bean = (PictureBean) data.getExtras().get(PictureSelector.PICTURE_RESULT);
            Glide.with(getContext()).load(bean.getPath()).transform(new CircleCrop()).into(ivMineHead);
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
        EventBus.getDefault().unregister(this);
        UserCommon.isLogin = false;
        Log.d("ljl", "onDestroy: 销毁，切换到最初状态");
    }
}
