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
        CustomWebView.getInstance(newBase,getPackageName());
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
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upApp = mSharedPrefUtil.getValue("upApp");
                Log.e(TAG,upApp);
                CustomEventApi.sendEvent(MainActivity.this,upApp,"OpenAPP");

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

                mSharedPrefUtil.setSharedPref("upApp",value);
            }
        }
    }
}