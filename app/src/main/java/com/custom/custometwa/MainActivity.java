package com.custom.custometwa;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.io.upapp.CustomEventApi;
import com.io.upapp.http.VisitorInfoCallback;
import com.io.upapp.http.body.DetailBody;
import com.io.upapp.http.model.VisitorModel;
import com.io.upapp.util.ToolUtils;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "PostMessageDemo";
    private TextView mtextView,mTitle;
    private Button btn;
    private ProgressBar loading;

    private SharedPrefUtil mSharedPrefUtil;
    private Handler handler;

    Uri data = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefUtil = new SharedPrefUtil(this);
        loading = findViewById(R.id.loading);
        mTitle = findViewById(R.id.tv_title);
        mtextView = findViewById(R.id.textView);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // 更新 UI
                String responseText = (String) msg.obj;
                Log.d("visitorInfo",responseText);
                mtextView.setText(responseText);
            }
        };
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> {
            mTitle.setText("事件参数");
            loading.setVisibility(View.VISIBLE);
            DetailBody body = new DetailBody("OpenAPP");
            body.setDescription("web page");
            body.setPrice(1.00);
            body.setCurrency("USD");
            body.setBrand("Fancy Sneakers");

            CustomEventApi.sendEvent(MainActivity.this,body);
            loading.setVisibility(View.GONE);
            Message msg = handler.obtainMessage();
            msg.obj = ToolUtils.beanToString(body);
            handler.sendMessage(msg);
//                CustomEventApi.sendFirebaseAnalyticsEvent(MainActivity.this,body);
        });
        Button btnGet = findViewById(R.id.btn_get);
        btnGet.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            CustomEventApi.getVisitorInfo(MainActivity.this, new VisitorInfoCallback() {
                @Override
                public void onSuccess(VisitorModel visitorInfo) {
                    loading.setVisibility(View.GONE);
                    if (visitorInfo!=null){
                        mTitle.setText("访客信息");
                        Message msg = handler.obtainMessage();
                        msg.obj = ToolUtils.beanToString(visitorInfo);
                        handler.sendMessage(msg);
                    }
                }

                @Override
                public void onError(String err) {
                    loading.setVisibility(View.GONE);
                    Log.e("push",err);
                }
            });

        });

        Button btnGetParam = findViewById(R.id.btn_get_param);
        btnGetParam.setOnClickListener(v -> {
            loading.setVisibility(View.VISIBLE);
            CustomEventApi.getVisitorInfo(MainActivity.this, new VisitorInfoCallback() {
                @Override
                public void onSuccess(VisitorModel visitorInfo) {
                    loading.setVisibility(View.GONE);
                    mTitle.setText("着陆页参数");
                    if (visitorInfo!=null){
                        Message msg = handler.obtainMessage();
                        msg.obj = ToolUtils.jsonStringToMap(visitorInfo.getPostParam());
                        handler.sendMessage(msg);
                    }
                }
                @Override
                public void onError(String err) {
                    loading.setVisibility(View.GONE);
                    Log.e("push",err);
                }
            });
        });
        handleIntent(getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPrefUtil.clear();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @SuppressLint("SetTextI18n")
    private void handleIntent(Intent intent) {
        data = intent.getData();
        if (data != null && "io".equals(data.getScheme())) {
            Log.e(TAG,data.getScheme());
            Log.e(TAG, Objects.requireNonNull(data.getHost()));
            String action = data.getHost();
            if ("saveData".equals(action)) {
                List<String> params = data.getPathSegments();
                String key = params.get(0);
                String value = params.get(1);
                Log.e(TAG,key+" = "+value);
//                mtextView.setText(key+" = "+value);
                mTitle.setText("接收着陆页的信息");
                Message msg = handler.obtainMessage();
                msg.obj = ToolUtils.jsonStringToMap(value);
                handler.sendMessage(msg);
                loading.setVisibility(View.GONE);
                CustomEventApi.saveValue(MainActivity.this, value);
            }
        }
    }
}