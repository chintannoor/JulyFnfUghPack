package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivitySettingBinding;
import com.fnf.game.ugh.pack.more.util.InterstialAdInterface;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();
        setUpMediumNativeAd();
        setUpBannerAd();

        binding.gameToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.openNewAdActivity(SettingActivity.this);
                onBackPressed();
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.openNewAdActivity(SettingActivity.this);
            }
        });

        // New Reate UsClick
        binding.rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(SettingActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(SettingActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(SettingActivity.this);

                    NetworkUtil.loadInterstialAd(SettingActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                try {
                                    //Set id
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                                } catch (ActivityNotFoundException e) {
                                    //Set id
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                                }
                            }
                        }

                        @Override
                        public void isInterstialAdLoaded(boolean loadedStatus) {
                            if(loadedStatus)
                            {
                                alertDialog.dismiss();
                            }

                        }
                    });
                }
                else {
                    try {
                        //Set id
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                    } catch (ActivityNotFoundException e) {
                        //Set id
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }

            }
        });
        // New share Click
        binding.shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(SettingActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(SettingActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(SettingActivity.this);

                    NetworkUtil.loadInterstialAd(SettingActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                Intent myIntent = new Intent(Intent.ACTION_SEND);
                                myIntent.setType("text/plain");
                                myIntent.putExtra(Intent.EXTRA_SUBJECT, "Share app link : ");
                                myIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(Intent.createChooser(myIntent, "Share app"));
                            }
                        }

                        @Override
                        public void isInterstialAdLoaded(boolean loadedStatus) {
                            if(loadedStatus)
                            {
                                alertDialog.dismiss();
                            }

                        }
                    });
                }
                else {
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, "Share app link : ");
                    myIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(myIntent, "Share app"));
                }

            }
        });

    }
    //    // Native Ad Function
    private void setUpMediumNativeAd() {
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.MEDIUM_NATIVE_ADS_STATUS,
                1);
        if (status == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.MEDIUM_NATIVE_ADS,
                    "");
            NetworkUtil.loadNativeAd(binding.homeActivityNativeAd,context, adId);
        }
    }
    private void setUpBannerAd() {
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.BANNER_AD_STATUS,
                1);
        if (status == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.BANNER_AD,
                    "");
            NetworkUtil.adaptiveBannerAdSetup(this,context,adId,binding.homeActivityBannerAd);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NetworkUtil.openNewAdActivity(this);
    }
}