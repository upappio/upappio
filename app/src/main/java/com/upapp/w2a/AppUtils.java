package com.upapp.w2a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static void IntentActivity(Context context, Class<? extends AppCompatActivity> activity,boolean isClear) {
        Intent intent = new Intent(context, activity);
        if (isClear){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public static void IntentActivityByParam(Context context, Class<? extends AppCompatActivity> activity, String key,String value) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(key, value);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }else {
            Network[] networks = cm.getAllNetworks();
            for (Network network : networks) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
                if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressLint("SimpleDateFormat")
    public static String conversionDateForAMPM(String oldDate, boolean isOnlyHour, boolean isNotTime) {
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = inputFormat.parse(oldDate);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a", new java.util.Locale("en"));
            if (isOnlyHour){
                outputFormat = new SimpleDateFormat("hh:mm a", new java.util.Locale("en"));
            }
            if (isNotTime){
                outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            }
            assert date != null;
            return outputFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
