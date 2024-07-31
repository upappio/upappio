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

import com.io.upapp.http.ApiMethods;
import com.io.upapp.http.MyObserver;
import com.io.upapp.http.ObserverOnNextListener;
import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.model.BaseR;
import com.io.upapp.http.model.W2aModel;


public class CustomWebView {
    private static final String TAG = "PostMessageDemo";
    private static CustomTabsClient mClient;
    private static CustomTabsSession mSession;

    private static  String mPackageName;

    private static boolean mValidated = false;

    @SuppressLint("StaticFieldLeak")
    private static CustomWebView mCustomWebView;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext = null;

    public CustomWebView(Context context,String packageName) {
        mContext = context;
        mPackageName = packageName;
    }

    public static CustomWebView getInstance(Context context,String packageName){
        if (mCustomWebView == null){
            mCustomWebView = new CustomWebView(context,packageName);
            bindCustomTabsService();
        }
        return mCustomWebView;
    }

    public static void bindCustomTabsService() {

        ApiMethods.getAppInfo(new MyObserver(mContext, (ObserverOnNextListener<BaseR<W2aModel>>) w2aModelBaseR -> {
            if (w2aModelBaseR == null){
                return;
            }
            int code = w2aModelBaseR.getCode();
            if (code == 200) {
                W2aModel w2aModel = w2aModelBaseR.getData();
                W2aModel.InfoBean info = w2aModel.getInfo();
                String domain = info.getDomain();//TODO 获取着陆页url
                Uri uri = Uri.parse(domain);
                Uri appUrl = uri.buildUpon()
                        .appendQueryParameter("package", mPackageName)
                        .build();
                CustomTabsClient.bindCustomTabsService(mContext, "com.android.chrome",
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

                                        boolean result = mSession.requestPostMessageChannel(appUrl);
                                        Log.d(TAG, "Requested Post Message Channel: " + result);
                                    }

                                    @Override
                                    public void onMessageChannelReady(@Nullable Bundle extras) {
                                        Log.d(TAG, "Message channel ready.");

                                        int result = mSession.postMessage("First message", null);
                                        Log.d(TAG, "postMessage returned: " + result);
                                    }
                                });

                                launch(appUrl);
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName componentName) {
                                mClient = null;
                            }
                        });

            }
        }),new AppBody(mPackageName),mContext);


    }

    private static void launch(Uri URL) {
        Intent intent = new TrustedWebActivityIntentBuilder(URL).build(mSession).getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
