package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivityHowToPlayBinding;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

public class HowToPlayActivity extends AppCompatActivity {
        ActivityHowToPlayBinding binding;
        Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityHowToPlayBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            context = getApplicationContext();
            initui();
            setUpMediumNativeAd();
            setUpBannerAd();

            binding.gameToolBar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NetworkUtil.openNewAdActivity(HowToPlayActivity.this);
                    onBackPressed();
                }
            });
            binding.imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NetworkUtil.openNewAdActivity(HowToPlayActivity.this);
                }
            });

        }

        @SuppressLint("SetTextI18n")
        private void initui() {
            Intent intent = getIntent();
            if (intent.hasExtra("HowtoPlay")) {
                binding.titel.setText("How to Play");
                binding.imageView3.setImageResource(R.drawable.gamnenative2);
                binding.discription.setText("Complete the tutorial level and start progressing through\n" +
                        "the story, or head to free play mode and play against any\n" +
                        "story characters from the game. In story mode, you can\n" +
                        "set the level of difficulty that best suits your ability.");


            }
            else if(intent.hasExtra("Features")){
                binding.titel.setText("Features");
                binding.imageView3.setImageResource(R.drawable.gamef2);
                binding.discription.setText("Original music with a range of influences from nu-jazz to\n" +
                        "Vocaloid dance beats.\n" +
                        "Extensive career mode with plenty of unique characters.\n" +
                        "Various dificulty modes available to suit your rhythmic\n" +
                        "ability.\n" +
                        "Cool 90s B-Boy visuals\n" +
                        "Also Known as FNF");


            }
            else {
                binding.titel.setText("Similar Games");
                binding.imageView3.setImageResource(R.drawable.tajmahel);
                binding.discription.setText("Rhythm games are great fun to play solo or with your\n" +
                        "frinds. The mechanics are simple, and whether you\n" +
                        "succeed often comes down to your sense of rhythm and\n" +
                        "visual cuse. Geometry dash is another tricky game where\n" +
                        "following rhythmic cues is essential.\n" +
                        "\n" +
                        "If you like this kind of game, check out Chainsaw Dance\n" +
                        "for more musical rhythms, adn browse our music games\n" +
                        "for the full collection.\n" +
                        " ");

            }
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
                NetworkUtil.adaptiveBannerAdSetup(this ,context,adId,binding.homeActivityBannerAd);
            }
        }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            NetworkUtil.openNewAdActivity(this);
        }
}