package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivityPlayGameBinding;
import com.fnf.game.ugh.pack.more.util.InterstialAdInterface;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

public class PlayGameActivity extends AppCompatActivity {

    ActivityPlayGameBinding binding;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();

        setUpMediumNativeAd();
        setUpBannerAd();

        binding.gameToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkUtil.openNewAdActivity(PlayGameActivity.this);
                onBackPressed();
            }
        });
        // New Game Button Click
        binding.playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(PlayGameActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(PlayGameActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(PlayGameActivity.this);

                    NetworkUtil.loadInterstialAd(PlayGameActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                Intent intent = new Intent(PlayGameActivity.this,WebActivity.class);
                                intent.putExtra("CATEGORY_PLAY_GAME","https://fnf.run3.io/ugh-pack/2/");
                                startActivity(intent);

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
                    startActivity(new Intent(PlayGameActivity.this, WebActivity.class));
                }

            }
        });
    }
    //    // Native Ad Function
    private void setUpMediumNativeAd() {
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.SMALL_NATIVE_AD_STATUS,
                1);
        if (status == 1) {
            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.SMALL_NATIVE_AD,
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