package com.example.common.thread;

import java.lang.ref.WeakReference;

public class MyRunnable implements Runnable {
    //对Runnable实现弱引用
    private WeakReference<ThreadInterface> threadInterface;

    public MyRunnable(ThreadInterface threadInterface) {
        this.threadInterface = new WeakReference<>(threadInterface);
    }

    //在run方法中实现回调抽象方法
    @Override
    public void run() {
        threadInterface.get().readDbCache();
    }
}
