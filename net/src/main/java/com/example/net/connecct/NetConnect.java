package com.example.net.connecct;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnect {
    private Context context;
    private static NetConnect netConnect;

    public static NetConnect getNetConnect() {
        if (netConnect == null) {
            netConnect = new NetConnect();
        }
        return netConnect;
    }

    public Context getContext() {
        return context;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
