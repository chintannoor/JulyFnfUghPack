package com.fnf.game.ugh.pack.more.api;

import com.fnf.game.ugh.pack.more.model.AdModel;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @Multipart
    @POST("app_wise_ads_data")
    Call<AdModel> getAdsData(@Part("app_id") RequestBody requestBody);
}
