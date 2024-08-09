package com.io.upapp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.MyObserver;
import com.io.upapp.http.ObserverOnNextListener;
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
            contentsBean.setQuantity(Math.toIntExact(body.getQuantity()));
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
        String upApp = sharedPrefUtil.getValue("visitorInfo");
        return new Gson().fromJson(upApp, AppBody.class);
    }

    public static void getVisitorInfo(Context mContext,String upApp){
        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(mContext);
        sharedPrefUtil.setSharedPref("visitorInfo",upApp);
        if (!TextUtils.isEmpty(upApp)){
            AppBody appBody = new Gson().fromJson(upApp, AppBody.class);
            ApiMethods.getVisitorInfo(new MyObserver(mContext, (ObserverOnNextListener<BaseR<VisitorModel>>) visitorModelBaseR -> {
                if (visitorModelBaseR.getCode()==200){
                    VisitorModel data = visitorModelBaseR.getData();
                    sharedPrefUtil.setSharedPref("advPlatform",data.getPlatform());
                }

            }),appBody,mContext);
        }
    }
}
