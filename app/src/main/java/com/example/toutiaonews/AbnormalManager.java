package com.example.toutiaonews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@SuppressLint("StaticFieldLeak")
public class AbnormalManager implements Thread.UncaughtExceptionHandler {
    private static AbnormalManager abnormalManager;
    private Context context;
    private String crashPath;

    public static AbnormalManager getAbnormalManager() {
        if (abnormalManager == null) {
            abnormalManager = new AbnormalManager();
        }
        return abnormalManager;
    }

    public void init(Context context) {
        this.context = context;
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

        //存储异常报告的目录
        crashPath = App.app.getExternalCacheDir() + "/crash/";
        Log.e("AbnormalManager", "init: " + crashPath);
        File file = new File(crashPath);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull final Throwable throwable) {
        Throwable cause = throwable.getCause();
        Log.d("AbnormalManager", "cause:" + throwable.getCause());

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "程序出现异常，请重新打开", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        });
        thread1.start();
        thread1.interrupt();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                keepLoad(throwable);
//                UpLoad(throwable);
            }
        });
        thread2.start();
        thread2.interrupt();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        killProcess();
    }

//    private void UpLoad(Throwable throwable) {
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(stringWriter);
//        throwable.printStackTrace(printWriter);
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("message", stringWriter.toString());
//        HttpManager.getHttpManager().getRetrofit().create(Api.class)
//                .upData(params)
//                .subscribeOn(Schedulers.io())
//                .map(new NetFunction<BaseBean<String>, String>())
//                .observeOn(Schedulers.io())
//                .subscribe(new BaseObserver<String>() {
//                    @Override
//                    public void success(String str) {
//                        Log.e("hq", "success: " + str);
//                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void error(String errorMessage) {
//                        Log.e("hq", "success: " + errorMessage);
//                    }
//                });
//    }

    private void keepLoad(Throwable throwable) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        //生成一个文件名
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = simpleDateFormat.format(new Date());

        File crashFile = new File(crashPath + timeStr + ".txt");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(crashFile);
            String s = stringWriter.toString();
            byte[] bytes = s.getBytes();
            int len = bytes.length;
            fileOutputStream.write(bytes, 0, len);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void killProcess() {
        //想停掉这个应用，必须确保当前所有的Activity已经finish后才可以结束该进程，否则当前进程会一直存在

//        for (Activity activity : CacheManager.getCacheManager().getActivityList()) {
//            activity.finish();
//        }

        Intent intent = new Intent();
        intent.setClass(App.app, MainActivity.class);
        App.app.startActivity(intent);
        System.exit(1);//结束进程
        Process.killProcess(Process.myPid());
    }
}
