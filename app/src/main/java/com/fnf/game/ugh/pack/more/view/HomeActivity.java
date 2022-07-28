package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivityHomeBinding;
import com.fnf.game.ugh.pack.more.databinding.AelertBoxBinding;
import com.fnf.game.ugh.pack.more.util.InterstialAdInterface;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        setUpMediumNativeAd();
        setUpBannerAd();

        binding.gameToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.openNewAdActivity(HomeActivity.this);
                onBackPressed();
            }
        });
        binding.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.openNewAdActivity(HomeActivity.this);
            }
        });
        binding.playgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(HomeActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(HomeActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(HomeActivity.this);

                    NetworkUtil.loadInterstialAd(HomeActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                startActivity(new Intent(HomeActivity.this, PlayGameActivity.class));
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
                    startActivity(new Intent(HomeActivity.this, PlayGameActivity.class));
                }

            }
        });

        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(HomeActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(HomeActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(HomeActivity.this);

                    NetworkUtil.loadInterstialAd(HomeActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                startActivity(new Intent(HomeActivity.this, SettingActivity.class));
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
                    startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                }

            }
        });
        binding.viewmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(HomeActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(HomeActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(HomeActivity.this);

                    NetworkUtil.loadInterstialAd(HomeActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                startActivity(new Intent(HomeActivity.this, ViewMoreActivity.class));
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
                    startActivity(new Intent(HomeActivity.this, ViewMoreActivity.class));
                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        showDialogBox();
    }

    // Back To show Dialog box //
    private void showDialogBox() {
        final Dialog dialog = new Dialog(this);
        AelertBoxBinding binding = AelertBoxBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.MEDIUM_NATIVE_ADS_STATUS,
                1);
        if (status == 1){
            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.MEDIUM_NATIVE_ADS,
                    "");
            NetworkUtil.loadNativeAd(binding.customDialogBoxSmallNative,context,adId);
        }
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        Log.d("DialogShowTAG", String.valueOf(dialog));
        binding.dialogBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                loadYesButtonInterAd();
            }
        });
        binding.dialogBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // Load Back Interstitial Ad //
    private void loadYesButtonInterAd() {
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.REWARD_AD_STATUS,
                1);
        if (status==1) {
            AlertDialog alertDialog = NetworkUtil.createLoaderAlertDialog(this);
            String adID = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.REWARD_AD,
                    "");

            NetworkUtil.loadInterstialAd(this, adID, new InterstialAdInterface() {
                @Override
                public void interstialAdStatus(boolean status) {
                    if (status) {
                        Intent intentDialog = new Intent(HomeActivity.this, ThankYouActivity.class);
                        startActivity(intentDialog);
                    }
                }

                @Override
                public void isInterstialAdLoaded(boolean loadedStatus) {
                    alertDialog.dismiss();
                }
            });
        }
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
}