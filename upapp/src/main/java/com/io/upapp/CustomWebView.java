package com.io.upapp;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.browser.trusted.TrustedWebActivityIntentBuilder;



public class CustomWebView {
    private static final String TAG = "PostMessageDemo";
    private static CustomTabsClient mClient;
    private static CustomTabsSession mSession;

    private static  String mDomain = "https://newo.qunfan.cc";

    private static boolean mValidated = false;

    @SuppressLint("StaticFieldLeak")
    private static CustomWebView mCustomWebView;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext = null;

    private CustomWebView(Context context) {
        mContext = context;
    }

    public CustomWebView(Context context,String domain) {
        mContext = context;
        mDomain = domain;
    }

    public static CustomWebView getInstance(Context context){
        if (mCustomWebView == null){
            mCustomWebView = new CustomWebView(context);
            bindCustomTabsService();
        }
        return mCustomWebView;
    }

    public static CustomWebView getInstance(Context context,String domain){
        if (mCustomWebView == null){
            mCustomWebView = new CustomWebView(context,domain);
            bindCustomTabsService();
        }
        return mCustomWebView;
    }

    private static final CustomTabsCallback customTabsCallback =
            new CustomTabsCallback() {
                @Override
                public void onPostMessage(@NonNull String message, @Nullable Bundle extras) {
                    super.onPostMessage(message, extras);
                    if (message.contains("ACK")) {
                        return;
                    }
                    Log.d(TAG, "Got message: " + message);
                }

                @Override
                public void onRelationshipValidationResult(int relation, @NonNull Uri requestedOrigin,
                                                           boolean result, @Nullable Bundle extras) {
                    Log.d(TAG, "Relationship result: " + result);
                    Log.d(TAG, "relation result: " + relation);
                    Log.d(TAG, "requestedOrigin result: " + requestedOrigin);
                    mValidated = result;
                }

                @Override
                public void onNavigationEvent(int navigationEvent, @Nullable Bundle extras) {
                    if (navigationEvent != NAVIGATION_FINISHED) {
                        return;
                    }

                    if (!mValidated) {
                        Log.d(TAG, "Not starting PostMessage as validation didn't succeed.");
                    }
                    Uri SOURCE_ORIGIN = Uri.parse(mDomain + "/pwa_app/page_view_test.html");
                    Uri TARGET_ORIGIN = Uri.parse(mDomain +  "/pwa_app/page_view_test.html");
                    boolean result = mSession.requestPostMessageChannel(SOURCE_ORIGIN);
                    Log.d(TAG, "Requested Post Message Channel: " + result);
                }

                @Override
                public void onMessageChannelReady(@Nullable Bundle extras) {
                    Log.d(TAG, "Message channel ready.");

                    int result = mSession.postMessage("First message", null);
                    Log.d(TAG, "postMessage returned: " + result);
                }
            };

    public static void bindCustomTabsService() {
        String packageName = "com.android.chrome";
        CustomTabsClient.bindCustomTabsService(mContext, packageName,
                new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(@NonNull ComponentName name,
                                                             @NonNull CustomTabsClient client) {
                        mClient = client;

                        mClient.warmup(0L);

                        mSession = mClient.newSession(customTabsCallback);

                        launch();
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {
                        mClient = null;

                    }
                });
    }

    private static void launch() {
        String appUrl = "/pwa_app/page_view_test.html?up_id=32";
        Uri URL = Uri.parse(mDomain + appUrl);
        Intent intent = new TrustedWebActivityIntentBuilder(URL).build(mSession).getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
//             new TrustedWebActivityIntentBuilder(URL).build(mSession)
//             .launchTrustedWebActivity(mContext);
    }

}
