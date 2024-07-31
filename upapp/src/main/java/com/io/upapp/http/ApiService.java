package com.io.upapp.http;


import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.body.DetailBody;
import com.io.upapp.http.model.BaseR;
import com.io.upapp.http.model.VisitorModel;
import com.io.upapp.http.model.W2aModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("w2a_api/piexl/send/fb/event")
    Observable<BaseR> sendFbEvent(@Body DetailBody bo);

    @POST("w2a_api/piexl/send/tiktok/event")
    Observable<BaseR> sendTiTokEvent(@Body DetailBody bo);

    @POST("w2a_api/piexl/send/kwai/event")
    Observable<BaseR> sendKwaiEvent(@Body DetailBody bo);

    @POST("w2a_api/wta/app_info")
    Observable<BaseR<W2aModel>> getAppInfo(@Body AppBody bo);

    @POST("w2a_api/wta/pixel")
    Observable<BaseR<VisitorModel>> getVisitorInfo(@Body AppBody bo);
}
