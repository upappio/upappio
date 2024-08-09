package com.io.upapp.http;


import com.io.upapp.http.body.AppBody;
import com.io.upapp.http.body.FBEventBody;
import com.io.upapp.http.body.KWEventBody;
import com.io.upapp.http.body.TTEventBody;
import com.io.upapp.http.model.BaseR;
import com.io.upapp.http.model.VisitorModel;
import com.io.upapp.http.model.W2aModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("w2a_api/wta/send/fb/event")
    Observable<BaseR> sendFbEvent(@Body FBEventBody bo);

    @POST("w2a_api/wta/send/tiktok/event")
    Observable<BaseR> sendTiTokEvent(@Body TTEventBody bo);

    @POST("w2a_api/wta/send/kwai/event")
    Observable<BaseR> sendKwaiEvent(@Body KWEventBody bo);

    @POST("w2a_api/wta/app_info")
    Observable<BaseR<W2aModel>> getAppInfo(@Body AppBody bo);

    @POST("w2a_api/wta/wvi")
    Observable<BaseR<VisitorModel>> getVisitorInfo(@Body AppBody bo);
}
