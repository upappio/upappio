package com.custom.custometwa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.io.upapp.CustomWebView;

public class SplashActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.Companion.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> true );
        if (!AppUtils.isNetworkConnected(getApplicationContext())) {
            Toast.makeText(this, "The phone is not connected to the Internet!", Toast.LENGTH_SHORT).show();
        }

        CustomWebView.bindCustomTabsService(this,"com.custom.custometwa",MainActivity.class);
    }
}
