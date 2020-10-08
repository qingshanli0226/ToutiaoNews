package com.example.common.runnable;

import java.lang.ref.WeakReference;

public class MyRunnable implements Runnable {
    private WeakReference<ThreadInterface> threadInterface;
    public MyRunnable(ThreadInterface threadInterface){
        this.threadInterface = new WeakReference<>(threadInterface);
    }
    @Override
    public void run() {
        threadInterface.get().readDbCache();
    }
}
