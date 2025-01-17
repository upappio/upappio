package com.io.upapp.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

public class ToolUtils {

    public static String beanToString(Object bean) {
        StringBuilder builder = new StringBuilder();
        Class<?> clazz = bean.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object value = field.get(bean);
                if (value!=null){
                    builder.append(key).append(": ").append(value).append("\n");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    public static String jsonStringToMap(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // 提取数据
            StringBuilder builder = new StringBuilder();
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                String value = jsonObject.getString(key);
                builder.append(key).append(": ").append(value).append("\n");
            }

            return builder.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

//获取所有网络接口的 IP
    public String getAllIpAddresses() {
        StringBuilder ipAddresses = new StringBuilder();
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddresses.append(inetAddress.getHostAddress()).append("\n");
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipAddresses.toString();
    }

    //获取公网 IP 地址
    public void getPublicIpAddress() {
        new Thread(() -> {
            try {
                URL url = new URL("https://api.ipify.org"); // 使用公共服务获取公网 IP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String publicIp = in.readLine();
                in.close();
                Log.d("Public IP", publicIp); // 这里输出设备的公网 IP
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    //获取设备局域网 IP
    public String getLocalIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            // 转换 IP 地址格式
            return String.format("%d.%d.%d.%d",
                    (ipAddress & 0xff),
                    (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff),
                    (ipAddress >> 24 & 0xff));
        }
        return null;
    }

}
