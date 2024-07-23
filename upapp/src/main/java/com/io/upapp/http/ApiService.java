package com.io.upapp.http;


import com.io.upapp.http.body.DetailBody;
import com.io.upapp.http.model.BaseR;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("pwa_api/piexl/send/fb/event")
    Observable<BaseR> sendFbEvent(@Body DetailBody bo);

    @POST("pwa_api/piexl/send/tiktok/event")
    Observable<BaseR> sendTiTokEvent(@Body DetailBody bo);

    @POST("pwa_api//piexl/send/kwai/event")
    Observable<BaseR> sendKwaiEvent(@Body DetailBody bo);

}
