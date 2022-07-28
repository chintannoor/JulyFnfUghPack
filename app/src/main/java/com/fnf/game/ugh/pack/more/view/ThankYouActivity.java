package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivityThankYouBinding;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;
import com.fnf.game.ugh.pack.more.util.StaticData;

public class ThankYouActivity extends AppCompatActivity {
        Context context;
        ActivityThankYouBinding binding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityThankYouBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            context = getApplicationContext();
            initClickEvent();
            setUpMediumNativeAd();

            // Qureka Button Click Ad
            binding.qurekanative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NetworkUtil.openNewAdActivity(ThankYouActivity.this);
                }
            });
        }

        // init click event //
        private void initClickEvent() {
            binding.thankyoubtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    moveTaskToBack(true);
                }
            });
        }

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
                NetworkUtil.loadNativeAd(binding.thankyouActivityNativeAd, context, adId);
            }
        }
}