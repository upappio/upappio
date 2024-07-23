package com.io.upapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {

    private static Handler mWorkerHandler;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private volatile static ToastUtil mToastUtil;

    public static ToastUtil getInstance(Context context) {
        ToastUtil.mContext = context;
        if (mToastUtil == null) {
            synchronized (ToastUtil.class) {
                if (mToastUtil == null) {
                    mToastUtil = new ToastUtil();
                }
            }
        }
        return mToastUtil;
    }

    static {
        Thread workerThread = new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                mWorkerHandler = new Handler();
                Looper.loop();
            }
        };
        workerThread.start();
    }

    public void showToast(String msg) {
        ShowToastRunnable runnable = new ShowToastRunnable(msg);
        if (mWorkerHandler != null) {
            mWorkerHandler.post(runnable);
        }
    }

    private static class ShowToastRunnable implements Runnable {
        private final String msg;

        ShowToastRunnable(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
