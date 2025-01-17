package com.io.upapp;

import static com.io.upapp.UpApp.mFirebaseAnalytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.EventBody;
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
import com.io.upapp.util.W2AContants;

import java.util.ArrayList;
import java.util.List;

public class CustomEventApi {


    private static AppBody getAppBody(Context context) {
        String upApp = getSharedPrefValue(context, W2AContants.PREF_UP_APP);
        if (TextUtils.isEmpty(upApp)) {
            String upUuid = getSharedPrefValue(context, W2AContants.PREF_UP_UUID);
            String userPackageName = getSharedPrefValue(context, W2AContants.PREF_USER_PACKAGENAME);
            String devKey = getSharedPrefValue(context, W2AContants.PREF_UP_DEV_KEY);
            return new AppBody(userPackageName,upUuid,devKey) ;
        }
        return new Gson().fromJson(upApp, AppBody.class);
    }
    public static void saveValue(Context context, String upApp) {
        if (!TextUtils.isEmpty(upApp)) {
            saveSharedPrefValue(context, W2AContants.PREF_UP_APP, upApp);
        }
        getVisitorInfo(context, new VisitorInfoCallback() {
            @Override
            public void onSuccess(VisitorModel visitorInfo) {
                saveSharedPrefValue(context, W2AContants.PREF_ADV_PLATFORM, visitorInfo.getPlatform());
                saveSharedPrefValue(context, W2AContants.PREF_UP_UUID, visitorInfo.getUpUuid());
                saveVisitorInfo(context, visitorInfo);
            }

            @Override
            public void onError(String error) {
                Log.e("AppManager", "Failed to get visitor info: " + error);
            }
        });
    }
    public static void getVisitorInfo(Context context,VisitorInfoCallback callback) {
        AppBody appBody = getAppBody(context);
        ApiMethods.getVisitorInfo(
                new MyObserver(context, (ObserverOnNextListener<BaseR<VisitorModel>>) visitorModelBaseR -> {
                    if (visitorModelBaseR.getCode() == 200) {
                        callback.onSuccess(visitorModelBaseR.getData());
                    } else {
                        callback.onError(visitorModelBaseR.getMsg());
                    }
                }),
                appBody,
                context
        );
    }
    public static void sendEvent(Context mContext, DetailBody body) {
        if (TextUtils.isEmpty(body.getEventName())) {
            ToastUtil.getInstance(mContext).showToast("Event name is required!");
            return;
        }

        String advPlatform = getSharedPrefValue(mContext,W2AContants.PREF_ADV_PLATFORM);
        if (TextUtils.isEmpty(advPlatform)) {
            ToastUtil.getInstance(mContext).showToast("Ad platform is not set.");
            return;
        }

        EventBody eventBody = createEventBody(mContext,advPlatform, body);
        if (eventBody != null) {
            sendEventToPlatform(mContext, advPlatform, eventBody);
        } else {
            ToastUtil.getInstance(mContext).showToast("Unsupported ad platform.");
        }
    }
    private static void sendEventToPlatform(Context mContext, String platform, EventBody eventBody) {
        AppBody appBody = getAppBody(mContext);
        eventBody.setUpUuid(appBody.getUpUuid());
        eventBody.setDevKey(appBody.getDevKey());

        switch (platform) {
            case "Facebook":
                ApiMethods.sendFBEvent(mContext, (FBEventBody) eventBody);
                break;
            case "TikTok":
                ApiMethods.sendTiTokEvent(mContext, (TTEventBody) eventBody);
                break;
            case "KWai":
                ApiMethods.sendKwaiEvent(mContext, (KWEventBody) eventBody);
                break;
            default:
                break;
        }
    }

    private static EventBody createEventBody(Context mContext, String platform, DetailBody body) {
        switch (platform) {
            case "Facebook":
                return createFBEventBody(body);
            case "TikTok":
                return createTTEventBody(body);
            case "KWai":
                return createKWEventBody(body);
            case "Google":
                sendFirebaseAnalyticsEvent(mContext, body);  // Already handled separately
                return null;
            default:
                return null; // Unsupported platform
        }
    }


    private static FBEventBody createFBEventBody(DetailBody body) {
        FBEventBody fbEventBody = new FBEventBody();
        fbEventBody.setEvent_name(body.getEventName());

        FBEventBody.CustomDataBean customDataBean = new FBEventBody.CustomDataBean();
        customDataBean.setCurrency(body.getCurrency());
        customDataBean.setValue(body.getPrice());

        List<FBEventBody.CustomDataBean.ContentsBean> contentsBeans = new ArrayList<>();
        FBEventBody.CustomDataBean.ContentsBean contentsBean = new FBEventBody.CustomDataBean.ContentsBean();
        contentsBean.setBrand(body.getBrand());
        contentsBean.setDescription(body.getDescription());
        contentsBean.setId(body.getContentName());
        contentsBean.setQuantity(body.getQuantity());
        contentsBeans.add(contentsBean);

        customDataBean.setContents(contentsBeans);
        fbEventBody.setCustom_data(customDataBean);

        return fbEventBody;
    }

    private static TTEventBody createTTEventBody(DetailBody body) {
        TTEventBody ttEventBody = new TTEventBody();
        List<TTEventBody.DataBean> dataBeanList = new ArrayList<>();
        TTEventBody.DataBean dataBean = new TTEventBody.DataBean();

        dataBean.setEvent(body.getEventName());
        TTEventBody.DataBean.PropertiesBean propertiesBean = new TTEventBody.DataBean.PropertiesBean();
        propertiesBean.setContent_type(body.getContentType());
        propertiesBean.setDescription(body.getDescription());

        List<TTEventBody.DataBean.PropertiesBean.ContentsBean> contentsBeans = new ArrayList<>();
        TTEventBody.DataBean.PropertiesBean.ContentsBean contentsBean = new TTEventBody.DataBean.PropertiesBean.ContentsBean();
        contentsBean.setBrand(body.getBrand());
        contentsBean.setPrice(body.getPrice());
        contentsBean.setQuantity(body.getQuantity());
        contentsBean.setContent_name(body.getContentName());
        contentsBean.setContent_category(body.getContentCategory());
        contentsBean.setContent_id(body.getContentId());
        contentsBeans.add(contentsBean);

        propertiesBean.setCurrency(body.getCurrency());
        propertiesBean.setValue(body.getPrice());
        propertiesBean.setContents(contentsBeans);

        dataBean.setProperties(propertiesBean);
        dataBeanList.add(dataBean);
        ttEventBody.setData(dataBeanList);

        return ttEventBody;
    }

    private static KWEventBody createKWEventBody(DetailBody body) {
        KWEventBody kwEventBody = new KWEventBody();
        KWEventBody.PropertiesBean propertiesBean = new KWEventBody.PropertiesBean();
        propertiesBean.setContent_id(body.getContentId());
        propertiesBean.setContent_name(body.getContentName());
        propertiesBean.setContent_type(body.getContentType());
        kwEventBody.setProperties(propertiesBean);

        return kwEventBody;
    }

    public static void sendFirebaseAnalyticsEvent(Context mContext, DetailBody body) {
        AppBody appBody = getAppBody(mContext);
        assert appBody != null;

        Bundle bundle = new Bundle();
        String upUuid = appBody.getUpUuid();

        if (!TextUtils.isEmpty(upUuid)) {
            bundle.putString("event_id", upUuid);
            bundle.putString(FirebaseAnalytics.Param.ACHIEVEMENT_ID, upUuid);
        }

        addEventParam(bundle, FirebaseAnalytics.Param.CURRENCY, body.getCurrency());
        addEventParam(bundle, FirebaseAnalytics.Param.ITEM_BRAND, body.getBrand());
        addEventParam(bundle, FirebaseAnalytics.Param.ITEM_ID, body.getContentId());
        addEventParam(bundle, FirebaseAnalytics.Param.ITEM_NAME, body.getContentName());
        addEventParam(bundle, FirebaseAnalytics.Param.PRICE, String.valueOf(body.getPrice()));
        addEventParam(bundle, FirebaseAnalytics.Param.QUANTITY, String.valueOf(body.getQuantity()));

        mFirebaseAnalytics.logEvent(body.getEventName(), bundle);
    }

    private static void addEventParam(Bundle bundle, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            bundle.putString(key, value);
        }
    }

    private static String getSharedPrefValue(Context context, String key) {
        return new SharedPrefUtil(context).getValue(key);
    }

    private static void saveSharedPrefValue(Context context, String key, String value) {
        new SharedPrefUtil(context).setSharedPref(key, value);
    }

    private static void saveVisitorInfo(Context context, VisitorModel visitorInfo) {
        new SharedPrefUtil(context).putBean(W2AContants.PREF_VISITOR_INFO, visitorInfo);
    }
}
