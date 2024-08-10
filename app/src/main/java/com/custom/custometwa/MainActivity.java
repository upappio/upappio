package com.custom.custometwa;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.io.upapp.CustomEventApi;
import com.io.upapp.CustomWebView;
import com.io.upapp.http.body.DetailBody;

import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "PostMessageDemo";
    private TextView mtextView;
    private Button btn;

    private SharedPrefUtil mSharedPrefUtil;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (newBase == null) {
            return;
        }
        CustomWebView.getInstance(newBase,"com.custom.custometwa");
        super.attachBaseContext(newBase);
    }

    Uri data = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefUtil = new SharedPrefUtil(this);

        mtextView = findViewById(R.id.textView);
        String upApp = mSharedPrefUtil.getValue("upApp");
        mtextView.setText(upApp);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailBody body = new DetailBody("OpenAPP");
                body.setDescription("web page");
                body.setPrice(1.00);
                body.setCurrency("USD");
                body.setBrand("Fancy Sneakers");
//                CustomEventApi.sendEvent(MainActivity.this,body);
                CustomEventApi.sendFirebaseAnalyticsEvent(MainActivity.this,body);
            }
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
                mtextView.setText(key+" = "+value);

                CustomEventApi.getVisitorInfo(MainActivity.this,value);
            }
        }
    }
}