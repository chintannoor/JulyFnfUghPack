package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivitySpinerBinding;
import com.fnf.game.ugh.pack.more.databinding.DialogWinnerLostBinding;
import com.fnf.game.ugh.pack.more.util.InterstialAdInterface;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinerActivity extends AppCompatActivity {


    ActivitySpinerBinding binding;
    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context= getApplicationContext();
        setUpMediumNativeAd();
        setUpBannerAd();
        binding.spinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupSpinner();
            }
        });

    }

    // Setup Spinner //
    private void setupSpinner() {
        Random random = new Random();
        binding.spinnerButton.setEnabled(false);
        int spin = random.nextInt(20) + 10;
        spin = spin * 36;
        CountDownTimer timer = new CountDownTimer(spin * 5, 1) {
            @Override
            public void onTick(long l) {
                // rotate the wheel
                float rotation = binding.spinnerImage.getRotation() + 2;
                binding.spinnerImage.setRotation(rotation);
            }

            @Override
            public void onFinish() {
                // enabling the button again
                openDialog();
                binding.spinnerButton.setEnabled(true);
            }
        }.start();
    }


    // Open Dialog //
    @SuppressLint("ResourceType")
    private void openDialog() {
        List<String> list = new ArrayList<>();
        list.add("1000");
        list.add("2000");
        list.add("3000");
        list.add("4000");
        list.add("5000");
        list.add("6000");
        list.add("7000");
        list.add("8000");
        list.add("1000");

        DialogWinnerLostBinding dialogMainBinding;
        Dialog dialog;
        dialogMainBinding = DialogWinnerLostBinding.inflate(getLayoutInflater());
        dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.setContentView(dialogMainBinding.getRoot());
        int status = NetworkUtil.getSharedPreferenceStatus(this,
                StaticData.AD_SHARED_PREFERENCE_DATABASE,
                StaticData.SMALL_NATIVE_AD_STATUS,
                1);
        if (status == 1){
            String adId = NetworkUtil.getSharedPreferenceData(this,
                    StaticData.AD_SHARED_PREFERENCE_DATABASE,
                    StaticData.SMALL_NATIVE_AD,
                    "");
            NetworkUtil.loadNativeAd(dialogMainBinding.smallNative,context,adId);
        }

        String winningPrize = list.get(new Random().nextInt(list.size()));
        dialogMainBinding.quizDialogTitle.setText(new StringBuilder().append(winningPrize).append(" Coins").toString());
        dialogMainBinding.quizDialogSubTitle.setText(new StringBuilder().append("Your Won ").append(winningPrize).append(" Coins.Play games and win more.").toString());

        dialogMainBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int status = NetworkUtil.getSharedPreferenceStatus(SpinerActivity.this,
                        StaticData.AD_SHARED_PREFERENCE_DATABASE,
                        StaticData.INTERSTITIAL_AD_STATUS,
                        1);
                if(status == 1)
                {
                    String adId = NetworkUtil.getSharedPreferenceData(SpinerActivity.this,
                            StaticData.AD_SHARED_PREFERENCE_DATABASE,
                            StaticData.INTERSTITIAL_AD,"");
                    AlertDialog alertDialog =NetworkUtil.createLoaderAlertDialog(SpinerActivity.this);

                    NetworkUtil.loadInterstialAd(SpinerActivity.this, adId, new InterstialAdInterface() {
                        @Override
                        public void interstialAdStatus(boolean status) {
                            if (status){
                                Intent intentDialog = new Intent(SpinerActivity.this, HomeActivity.class);
                                startActivity(intentDialog);
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
                    Intent intentDialog = new Intent(SpinerActivity.this, HomeActivity.class);
                    startActivity(intentDialog);
                }

            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
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
            NetworkUtil.loadNativeAd(binding.spinnerSmallNativeAd,context, adId);
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
            NetworkUtil.adaptiveBannerAdSetup(this,context,adId,binding.spinnerBannerAd);
        }
    }
}