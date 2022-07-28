package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.api.ApiService;
import com.fnf.game.ugh.pack.more.databinding.ActivityMainBinding;
import com.fnf.game.ugh.pack.more.model.AdModel;
import com.fnf.game.ugh.pack.more.model.AdSubModel;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BASE_URL = "http://apploginads.noorinfotech.in/";
    public static final String AD_URL = BASE_URL + "api/ads/";
    private ActivityMainBinding binding;
    private Context context;
    private static final int APP_UPDATE_CODE = 200;


    private static final String TAG = "AdTAG";
    private static final String APP_STATUS_TAG = "APP_STATUS_TAG";
    private static final String APP_ID_TAG = "AppIdTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();
        initClickListener();
        callApi();

    }
    public void callApi(){
        RequestBody requestBody = RequestBody.create(String.valueOf(151), MediaType.parse("text/plain"));
        ApiService.getClient(AD_URL).getAdsData(requestBody).enqueue(new Callback<AdModel>() {
            @Override
            public void onResponse(Call<AdModel> call, Response<AdModel> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        setUpAdPreference(response.body().getData());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.launchLoader.setVisibility(View.GONE);
                                binding.launchStartButton.setVisibility(View.VISIBLE);

                            }
                        },3000);
                    }
                }
            }

            @Override
            public void onFailure(Call<AdModel> call, Throwable t) {
                Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //* Set up ad in shared preference *//
    private void setUpAdPreference(List<AdSubModel> list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(StaticData.AD_SHARED_PREFERENCE_DATABASE, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (AdSubModel adSubModel : list) {
            String adName = adSubModel.getAdsName();
            String adId = adSubModel.getAdsLink();
            int status = adSubModel.getAdsStatus();

//            Log.d("MAIN_AD_TAG",adName.toString());
//            Log.d("MAIN_AD_TAG",adId.toString());
//            Log.d("MAIN_AD_TAG","Thank");

            if (adName.equals(StaticData.APP_ID)) {
                setUpAppId(adId);
            }
            else if (adName.equals(StaticData.SMALL_NATIVE_AD)) {
                editor.putString(StaticData.SMALL_NATIVE_AD, adId);
                editor.putInt(StaticData.SMALL_NATIVE_AD_STATUS,status);
                editor.apply();
            }else if (adName.equals(StaticData.MEDIUM_NATIVE_ADS)) {
                editor.putString(StaticData.MEDIUM_NATIVE_ADS, adId);
                editor.putInt(StaticData.MEDIUM_NATIVE_ADS_STATUS,status);
                editor.apply();
            } else if (adName.equals(StaticData.BANNER_AD)) {
                editor.putString(StaticData.BANNER_AD, adId);
                editor.putInt(StaticData.BANNER_AD_STATUS,status);
                editor.apply();
            } else if (adName.equals(StaticData.REWARD_AD)) {
                editor.putString(StaticData.REWARD_AD, adId);
                editor.putInt(StaticData.REWARD_AD_STATUS,status);
                editor.apply();
            } else {
                editor.putString(StaticData.INTERSTITIAL_AD, adId);
                editor.putInt(StaticData.INTERSTITIAL_AD_STATUS,status);
                editor.apply();
            }
        }
    }

    //* Setup App Id *//
    private void setUpAppId(String appId) {
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("applovin.sdk.key");
            Log.d(APP_ID_TAG, "Name Found: " + myApiKey);
            ai.metaData.putString("applovin.sdk.key", appId);//you can replace your key APPLICATION_ID here
            String ApiKey = bundle.getString("applovin.sdk.key");
            Log.d(APP_ID_TAG, "ReNamed Found: " + ApiKey);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(APP_ID_TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(APP_ID_TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
    }

    //* init CLick Event *//
    private void initClickListener(){
        binding.launchStartButton.setOnClickListener(this);
    }
    //* ALl CLuixck
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.launchStartButton:
                if (NetworkUtil.isNetworkConnected(context)){
                    Intent intent = new Intent(MainActivity.this,SpinerActivity.class);
                    startActivity(intent
                    );
                }else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}