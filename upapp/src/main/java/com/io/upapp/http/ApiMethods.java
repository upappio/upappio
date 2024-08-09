package com.io.upapp.http;



import android.content.Context;



import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.body.FBEventBody;
import com.io.upapp.http.body.KWEventBody;
import com.io.upapp.http.body.TTEventBody;
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

    public static void sendFbEvent(Observer observer, FBEventBody bo, Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendFbEvent(bo), mContext, observer);
    }
    public static void sendTiTokEvent(Observer observer, TTEventBody bo,Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendTiTokEvent(bo), mContext, observer);
    }
    public static void sendKwaiEvent(Observer observer, KWEventBody bo, Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().sendKwaiEvent(bo), mContext, observer);
    }

    public static void getAppInfo(Observer observer, AppBody bo, Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().getAppInfo(bo), mContext, observer);
    }


    public static void getVisitorInfo(Observer observer, AppBody bo, Context mContext) {
        ApiSubscribe(ApiStrategy.getApiService().getVisitorInfo(bo), mContext, observer);
    }


    public static void sendFBEvent(Context context,FBEventBody bo) {
        ObserverOnNextListener<BaseR> listenerInfo = reposeUserInfo -> {
            if (reposeUserInfo == null)
                return;
        };
        sendFbEvent(new MyObserver(context, listenerInfo),bo,context);

    }
    public static void sendTiTokEvent(Context context, TTEventBody bo) {
        ObserverOnNextListener<BaseR> listenerInfo = reposeUserInfo -> {
            if (reposeUserInfo == null)
                return;
        };
        sendTiTokEvent(new MyObserver(context, listenerInfo),bo,context);
    }
    public static void sendKwaiEvent(Context context,KWEventBody bo) {
        ObserverOnNextListener<BaseR> listenerInfo = reposeUserInfo -> {
            if (reposeUserInfo == null)
                return;
        };
        sendKwaiEvent(new MyObserver(context, listenerInfo),bo,context);
    }

}
