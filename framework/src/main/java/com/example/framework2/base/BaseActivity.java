package com.example.framework2.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.framework2.listener.PermissionListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class BaseActivity extends AppCompatActivity {

    private static Activity mCurrentActivity;// 对所有activity进行管理
    public static List<Activity> mActivities = new LinkedList<Activity>();
    //动态权限回调
    private PermissionListener mPermissionListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            mActivities.add(this);
        }

        initView();
        initData();
    }

    //打印log
    public void printLog(String message) {
        Log.d("a", "ActivityLog---->" + message);
    }

    //短吐司
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //初始化控件
    protected abstract void initData();

    //初始化数据
    protected abstract void initView();

    //绑定布局xml
    protected abstract int getLayout();

    //Activity跳转
    public void launchActivity(Class launchActivityClass, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        Intent intent = new Intent(this, launchActivityClass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 退出应用的方法
     */
    public static void exitApp() {
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentActivity != null) {
            mCurrentActivity = this;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCurrentActivity != null) {
            mCurrentActivity = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁的时候从集合中移除
        synchronized (mActivities) {
            mActivities.remove(this);
            this.finish();
        }
    }

    /**
     * 申请运行时权限
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            permissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

}
