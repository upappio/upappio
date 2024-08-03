package com.io.upapp;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.body.DetailBody;

public class CustomEventApi {


    public static void sendEvent(Context mContext,String upApp,String eventName){
        if (!TextUtils.isEmpty(upApp)){
            DetailBody detailBody = new Gson().fromJson(upApp, DetailBody.class);
            detailBody.setEventName(eventName);
            ApiMethods.sendEvent(mContext,detailBody);
        }

    }
}
