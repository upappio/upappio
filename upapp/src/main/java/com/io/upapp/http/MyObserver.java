package com.io.upapp.http;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;



import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import com.io.upapp.http.model.BaseR;
import com.io.upapp.util.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public class MyObserver<T> implements Observer<BaseR<T>> {
    private static final String TAG = "observer";
    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(Context context, ObserverOnNextListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(TAG, "onSubscribe: ");
    }

    @Override
    public void onNext(BaseR<T> value) {
        if (value==null)return;
        if (value.getCode() == 200){
            if (listener != null) {
                listener.onNext(value.getData());
            }
        }else {
            ToastUtil.getInstance(context).showToast(value.getMsg());
        }
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public void onError(Throwable e) {
        try {
            onNext(null);
            if (e instanceof SocketTimeoutException) {
                ToastUtil.getInstance(context).showToast("Request timed out");
            } else if (e instanceof ConnectException) {
                ToastUtil.getInstance(context).showToast("Network connection timed out");
            } else if (e instanceof SSLHandshakeException) {//Security certificate exception
                ToastUtil.getInstance(context).showToast("Security certificate exception");
            } else if (e instanceof HttpException) {//The requested address does not exist
                int code = ((HttpException) e).code();
                if (code == 504) {
                    ToastUtil.getInstance(context).showToast("Network abnormality, please check your network status");
                } else if (code == 500) {
                    ToastUtil.getInstance(context).showToast("Internal Server Error");
                } else if (code == 404) {
                    ToastUtil.getInstance(context).showToast("The requested address does not exist");
                } else {
                    Log.e("OnSuccessAndFaultSub", "Request failed: " + code);
                }
            } else if (e instanceof UnknownHostException) {
                ToastUtil.getInstance(context).showToast("Domain name resolution failed");
            } else {
                Log.e("OnSuccessAndFaultSub", "error:" + e.getMessage());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        } finally {
            Log.e("OnSuccessAndFaultSubfinally", "error:" + e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete: ");
    }

}
