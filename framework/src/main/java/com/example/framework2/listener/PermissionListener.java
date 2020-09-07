package com.example.framework2.listener;

import java.util.List;

public interface PermissionListener {
    /**
     * 权限申请回调的接口
     * deniedPermissions 被用户拒绝的权限
     */
    void onGranted();//授予权限

    void onDenied(List<String> deniedPermissions);//拒绝授权

}
