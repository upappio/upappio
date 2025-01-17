package com.io.upapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.browser.trusted.TrustedWebActivityIntentBuilder;

import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.MyObserver;
import com.io.upapp.http.ObserverOnNextListener;
import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.model.BaseR;
import com.io.upapp.http.model.W2aModel;
import com.io.upapp.util.SharedPrefUtil;
import com.io.upapp.util.W2AContants;


public class CustomWebView {
    private static CustomTabsClient mClient;
    private static CustomTabsSession mSession;

    private static boolean mValidated = false;
    private static SharedPrefUtil mSharedPrefUtil;

    public static void bindCustomTabsService(Context context,String packageName,Class<?> cls) {
        mSharedPrefUtil = new SharedPrefUtil(context);
        boolean isFirstTime = mSharedPrefUtil.getBolValue("isFirstTime",true);
        mSharedPrefUtil.setSharedPref(W2AContants.PREF_USER_PACKAGENAME,packageName);
        if (isFirstTime){
            ApiMethods.getAppInfo(new MyObserver(context, (ObserverOnNextListener<BaseR<W2aModel>>) w2aModelBaseR -> {
                if (w2aModelBaseR == null){
                    launchFallbackActivity(context, cls);
                    return;
                }
                if (w2aModelBaseR.getCode() == 200) {
                    W2aModel w2aModel = w2aModelBaseR.getData();
                    if (w2aModel == null){
                        launchFallbackActivity(context, cls);
                        return;
                    }

                    W2aModel.InfoBean app = w2aModel.getInfo();
                    if (app != null){
                        mSharedPrefUtil.setSharedPref(W2AContants.PREF_UP_DEV_KEY,app.getDevKey());
                    }
                    W2aModel.SiteBean site = w2aModel.getSite();
                    String landingUrl = site.getLandingUrl();
                    String landingUrlStatus = site.getLandingUrlStatus();
                    if ((TextUtils.isEmpty(landingUrl) || landingUrlStatus.equals("0")) ){
                        launchFallbackActivity(context, cls);
                    }else {
                        Uri uri = Uri.parse(landingUrl);
                        Uri appUrl = uri.buildUpon()
                                .appendQueryParameter("package", packageName)
                                .build();
                        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome",
                                new CustomTabsServiceConnection() {
                                    @Override
                                    public void onCustomTabsServiceConnected(@NonNull ComponentName name,
                                                                             @NonNull CustomTabsClient client) {
                                        mClient = client;

                                        mClient.warmup(0L);

                                        mSession = mClient.newSession( new CustomTabsCallback() {
                                            @Override
                                            public void onPostMessage(@NonNull String message, @Nullable Bundle extras) {
                                                super.onPostMessage(message, extras);
                                                if (message.contains("ACK")) {
                                                    return;
                                                }
                                                Log.d(W2AContants.TAG, "Got message: " + message);
                                            }

                                            @Override
                                            public void onRelationshipValidationResult(int relation, @NonNull Uri requestedOrigin,
                                                                                       boolean result, @Nullable Bundle extras) {
                                                Log.d(W2AContants.TAG, "Relationship result: " + result);
                                                Log.d(W2AContants.TAG, "relation result: " + relation);
                                                Log.d(W2AContants.TAG, "requestedOrigin result: " + requestedOrigin);
                                                mValidated = result;
                                            }

                                            @Override
                                            public void onNavigationEvent(int navigationEvent, @Nullable Bundle extras) {
                                                if (navigationEvent != NAVIGATION_FINISHED) {
                                                    return;
                                                }
                                                if (!mValidated) {
                                                    Log.d(W2AContants.TAG, "Not starting PostMessage as validation didn't succeed.");
                                                }

                                                boolean result = mSession.requestPostMessageChannel(appUrl);
                                                Log.d(W2AContants.TAG, "Requested Post Message Channel: " + result);
                                            }

                                            @Override
                                            public void onMessageChannelReady(@Nullable Bundle extras) {
                                                Log.d(W2AContants.TAG, "Message channel ready.");

                                                int result = mSession.postMessage("First message", null);
                                                Log.d(W2AContants.TAG, "postMessage returned: " + result);
                                            }
                                        });

                                        launch(context,appUrl);
                                    }

                                    @Override
                                    public void onServiceDisconnected(ComponentName componentName) {
                                        mClient = null;
                                    }
                                });

                    }

                }else{
                    launchFallbackActivity(context, cls);
                }
            }),new AppBody(packageName),context);
        }else{
            launchFallbackActivity(context, cls);
        }

    }

    private static void launch(Context context,Uri URL) {
        mSharedPrefUtil.setSharedPref("isFirstTime",false);
        Intent intent = new TrustedWebActivityIntentBuilder(URL).build(mSession).getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private static void launchFallbackActivity(Context context, Class<?> fallbackActivity) {
        Intent intent = new Intent(context, fallbackActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
