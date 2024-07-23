package com.io.upapp.http;



import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiStrategy {
//    public static String baseUrl = "https://api.keralalottery.cc";
    public static String baseUrl = " https://newo.qunfan.cc/";
    public static final int READ_TIME_OUT = 10000;
    public static final int CONNECT_TIME_OUT = 10000;

    public static ApiService apiService;

    @SuppressLint("StaticFieldLeak")
    public  static  Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ApiStrategy.context = context;
    }

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (ApiService.class) {
                if (apiService == null) {
                    new ApiStrategy();
                }
            }
        }
        return apiService;
    }

    private ApiStrategy() {

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor headerInterceptor = chain -> {
            Request.Builder requestBuilder = chain.request().newBuilder();
            Request build = requestBuilder.build();
            return chain.proceed(build);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .addInterceptor(new Retry(3))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static final long CACHE_STALE_SEC = 60 * 60 * 24;

    private final Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!isNetConnected(getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (isNetConnected(getContext())) {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "no-cache")
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" +
                            CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    public static boolean isNetConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    public static class Retry implements Interceptor {
        public int maxRetry;
        private int retryNum = 0;

        public Retry(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                response = chain.proceed(request);
            }
            return response;
        }
    }
}

