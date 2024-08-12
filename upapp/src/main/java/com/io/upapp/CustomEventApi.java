package com.io.upapp;

import static com.io.upapp.UpApp.mFirebaseAnalytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.MyObserver;
import com.io.upapp.http.ObserverOnNextListener;
import com.io.upapp.http.VisitorInfoCallback;
import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.body.DetailBody;
import com.io.upapp.http.body.FBEventBody;
import com.io.upapp.http.body.KWEventBody;
import com.io.upapp.http.body.TTEventBody;
import com.io.upapp.http.model.BaseR;
import com.io.upapp.http.model.VisitorModel;
import com.io.upapp.util.SharedPrefUtil;
import com.io.upapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class CustomEventApi {
    private static final String TAG = "PostMessageDemo";
    public static void sendEvent(Context mContext, DetailBody body){
        if (TextUtils.isEmpty(body.getEventName())){
            ToastUtil.getInstance(mContext).showToast("Event name is required!");
            return;
        }
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(mContext);
        String advPlatform = sharedPrefUtil.getValue("advPlatform");
        if ("Facebook".equals(advPlatform)){
            FBEventBody fbEventBody = new FBEventBody();
            fbEventBody.setEvent_name(body.getEventName());

            FBEventBody.CustomDataBean   customDataBean = new FBEventBody.CustomDataBean();
            customDataBean.setCurrency(body.getCurrency());
            customDataBean.setValue(body.getPrice());
            List<FBEventBody.CustomDataBean.ContentsBean>  contentsBeans = new ArrayList<>();
            FBEventBody.CustomDataBean.ContentsBean contentsBean = new FBEventBody.CustomDataBean.ContentsBean();
            contentsBean.setBrand(body.getBrand());
            contentsBean.setDescription(body.getDescription());
            contentsBean.setId(body.getContentName());
            contentsBean.setQuantity(body.getQuantity());
            contentsBeans.add(contentsBean);

            customDataBean.setContents(contentsBeans);
            fbEventBody.setCustom_data(customDataBean);

            sendFBEvent(mContext,fbEventBody);
        }else if ("TikTok".equals(advPlatform)){

            TTEventBody ttEventBody = new TTEventBody();
            List<TTEventBody.DataBean> dataBeanList =new ArrayList<>();
            TTEventBody.DataBean dataBean = new TTEventBody.DataBean();

            dataBean.setEvent(body.getEventName());
            TTEventBody.DataBean.PropertiesBean propertiesBean = new TTEventBody.DataBean.PropertiesBean();
            propertiesBean.setContent_type(body.getContentType());
            propertiesBean.setDescription(body.getDescription());

            List<TTEventBody.DataBean.PropertiesBean.ContentsBean>  contentsBeans = new ArrayList<>();
            TTEventBody.DataBean.PropertiesBean.ContentsBean contentsBean = new TTEventBody.DataBean.PropertiesBean.ContentsBean();
            contentsBean.setBrand(body.getBrand());
            contentsBean.setPrice( body.getPrice());
            contentsBean.setQuantity(body.getQuantity());
            contentsBean.setContent_name(body.getContentName());
            contentsBean.setContent_category(body.getContentCategory());
            contentsBean.setContent_id(body.getContentId());
            contentsBeans.add(contentsBean);

            propertiesBean.setCurrency(body.getCurrency());
            propertiesBean.setValue( body.getPrice());
            propertiesBean.setContents(contentsBeans);

            dataBean.setProperties(propertiesBean);
            dataBeanList.add(dataBean);
            ttEventBody.setData(dataBeanList);

            sendTTEvent(mContext,ttEventBody);
        }else if ("KWai".equals(advPlatform)){

            KWEventBody kwEventBody = new KWEventBody();
            KWEventBody.PropertiesBean propertiesBean = new KWEventBody.PropertiesBean();
            propertiesBean.setContent_id(body.getContentId());
            propertiesBean.setContent_name(body.getContentName());
            propertiesBean.setContent_type(body.getContentType());
            kwEventBody.setProperties(propertiesBean);
            sendKWEvent(mContext,kwEventBody);
        }else if ("Google".equals(advPlatform)){
            sendFirebaseAnalyticsEvent(mContext,body);
        }
    }
    private static void sendFBEvent(Context mContext, FBEventBody body){
        AppBody appBody = getAppBody(mContext);
        body.setUpUuid(appBody.getUpUuid());
        body.setDevKey(appBody.getDevKey());
        ApiMethods.sendFBEvent(mContext,body);
    }

    private static void sendTTEvent(Context mContext, TTEventBody body){
        AppBody appBody = getAppBody(mContext);
        body.setUpUuid(appBody.getUpUuid());
        body.setDevKey(appBody.getDevKey());
        ApiMethods.sendTiTokEvent(mContext,body);
    }

    private static void sendKWEvent(Context mContext, KWEventBody body){
        AppBody appBody = getAppBody(mContext);
        body.setUpUuid(appBody.getUpUuid());
        body.setDevKey(appBody.getDevKey());
        ApiMethods.sendKwaiEvent(mContext,body);
    }

    private static AppBody getAppBody(Context mContext) {
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(mContext);
        String upApp = sharedPrefUtil.getValue("upApp");
        Log.d("PostMessageDemo",upApp);
        if (TextUtils.isEmpty(upApp)){
            ToastUtil.getInstance(mContext).showToast("Please receive and pushValue ");
            return null;
        }
        return new Gson().fromJson(upApp, AppBody.class);
    }
    public static void saveValue(Context mContext,String upApp){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(mContext);
        if (TextUtils.isEmpty(upApp)){
            ToastUtil.getInstance(mContext).showToast("Please receive and pushValue ");
            return;
        }
        sharedPrefUtil.setSharedPref("upApp",upApp);
        getVisitorInfo(mContext, new VisitorInfoCallback() {
            @Override
            public void onSuccess(VisitorModel visitorInfo) {
                sharedPrefUtil.setSharedPref("advPlatform", visitorInfo.getPlatform());
                sharedPrefUtil.putBean("visitorInfo", visitorInfo);
            }

            @Override
            public void onError(String err) {

            }
        });
    }
    public static void getVisitorInfo(Context mContext,VisitorInfoCallback callback){

        AppBody appBody = getAppBody(mContext);
        ApiMethods.getVisitorInfo(new MyObserver(mContext, (ObserverOnNextListener<BaseR<VisitorModel>>) visitorModelBaseR -> {
            if (visitorModelBaseR.getCode() == 200){
                VisitorModel data = visitorModelBaseR.getData();
                callback.onSuccess(data);
            }else {
                callback.onError(visitorModelBaseR.getMsg());
            }
        }),appBody,mContext);
    }

    public static  void  sendFirebaseAnalyticsEvent(Context mContext, DetailBody body){

        AppBody appBody = getAppBody(mContext);
        assert appBody != null;
        String upUuid = appBody.getUpUuid();

        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(upUuid)){
            bundle.putString("event_id", upUuid);
            bundle.putString(FirebaseAnalytics.Param.ACHIEVEMENT_ID,upUuid);
        }

        if (!TextUtils.isEmpty(body.getCurrency())) {
            bundle.putString(FirebaseAnalytics.Param.CURRENCY, body.getCurrency());
        }

        if (!TextUtils.isEmpty(body.getBrand())) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_BRAND, body.getBrand());
        }

        if (!TextUtils.isEmpty(body.getContentId())) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, body.getContentId());
        }

        if (!TextUtils.isEmpty(body.getContentName())) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, body.getContentName());
        }

        if (body.getPrice() != 0) {
            bundle.putString(FirebaseAnalytics.Param.PRICE, String.valueOf(body.getPrice()));
        }

        if (body.getQuantity() != 0) {
            bundle.putString(FirebaseAnalytics.Param.QUANTITY, String.valueOf(body.getQuantity()));
        }

        mFirebaseAnalytics.logEvent(body.getEventName(), bundle);
    }
}
