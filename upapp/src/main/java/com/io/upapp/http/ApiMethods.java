package com.io.upapp.http;



import android.content.Context;


import java.util.List;

import com.io.upapp.http.body.DetailBody;
import com.io.upapp.http.model.BaseR;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {

    private static long RETRY_COUNT = 0;
    private ApiService apiService;

    public static void ApiSubscribe(Observable observable, Context mContext, Observer observer) {
        ApiStrategy.setContext(mContext);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)
                .subscribe(observer);
    }

    public static void sendFbEvent(Observer observer, DetailBody bo, Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendFbEvent(bo), mContext, observer);
    }
    public static void sendTiTokEvent(Observer observer, DetailBody bo,Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendTiTokEvent(bo), mContext, observer);
    }
    public static void sendKwaiEvent(Observer observer, DetailBody bo,Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendKwaiEvent(bo), mContext, observer);
    }

    public static void sendEvent(Context context,String channel,DetailBody bo) {
        ObserverOnNextListener<BaseR> listenerInfo = reposeUserInfo -> {
            if (reposeUserInfo == null)
                return;
            int code = reposeUserInfo.getCode();
            if (code == 200) {
            }
        };
        if ("FB".equals(channel)){
            sendFbEvent(new MyObserver(context, listenerInfo),bo,context);
        }else if ("TT".equals(channel)){
            sendTiTokEvent(new MyObserver(context, listenerInfo),bo,context);
        }else if ("KW".equals(channel)){
            sendKwaiEvent(new MyObserver(context, listenerInfo),bo,context);
        }
    }
}
