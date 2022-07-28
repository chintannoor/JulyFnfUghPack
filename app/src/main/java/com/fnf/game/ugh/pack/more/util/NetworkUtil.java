package com.fnf.game.ugh.pack.more.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.fnf.game.ugh.pack.more.R;
import com.google.android.material.card.MaterialCardView;

public class NetworkUtil {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void openNewAdActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://4876.play.quizzop.com/"));
        activity.startActivity(intent);
    }
    public static void openGameAdActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.gamezop.com/?id=4875"));
        activity.startActivity(intent);
    }
    // Statusbar Hide //
    public static void hideStatusbar(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    //* Navigation Activity *//
    public static Intent navActivity(Activity activityReference, Class className) {
        Intent intent = new Intent(activityReference, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    // Load interstial Ad //
    public static MaxInterstitialAd interstitialAd;
    public static void loadInterstialAd(Activity activity,String adId,com.fnf.game.ugh.pack.more.util.InterstialAdInterface adInterface){
        interstitialAd = new MaxInterstitialAd( adId, activity );
        MaxAdListener listener = new MaxAdListener() {

            @Override
            public void onAdLoaded(MaxAd ad) {
                adInterface.isInterstialAdLoaded(true);
                Log.d("InterstialAdTAG","On Ad Loaded");
                interstitialAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("InterstialAdTAG","On Ad Displayed");
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("InterstialAdTAG","On Ad Hidden");
                adInterface.interstialAdStatus(true);
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("InterstialAdTAG","On Ad Clicked");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("InterstialAdTAG","On Ad Failed");
                adInterface.isInterstialAdLoaded(true);
                adInterface.interstialAdStatus(true);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("InterstialAdTAG","On Ad Display Failed");
                adInterface.isInterstialAdLoaded(true);
                adInterface.interstialAdStatus(true);
            }
        };
        interstitialAd.setListener(listener);
        interstitialAd.loadAd();
    }

    // Load reward Ad //
    public static MaxRewardedAd rewardedAd;
    public static void loadRewardAd(Activity activity,String adId, com.fnf.game.ugh.pack.more.util.RewardAdInterface adInterface){
        rewardedAd = MaxRewardedAd.getInstance( adId, activity );
        MaxRewardedAdListener listener = new MaxRewardedAdListener() {
            @Override
            public void onRewardedVideoStarted(MaxAd ad) {

            }

            @Override
            public void onRewardedVideoCompleted(MaxAd ad) {

            }

            @Override
            public void onUserRewarded(MaxAd ad, MaxReward reward) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                adInterface.isRewordAdLoaded(true);
                Log.d("InterstialAdTAG","On Ad Loaded");
                rewardedAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                adInterface.isRewordAdLoaded(true);
                adInterface.rewordAdStatus(true);
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("InterstialAdTAG","On Ad Failed");
                adInterface.isRewordAdLoaded(true);
                adInterface.rewordAdStatus(true);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("InterstialAdTAG","On Ad Failed");
                adInterface.isRewordAdLoaded(true);
                adInterface.rewordAdStatus(true);
            }
        };
        rewardedAd.setListener(listener);
        rewardedAd.loadAd();
    }

    // Load Native Ad //
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd nativeAd = null;

    public static void loadNativeAd(MaterialCardView frameLayout, Context context, String adId) {
        nativeAdLoader = new MaxNativeAdLoader(adId, context);
        nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd);
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                frameLayout.removeAllViews();
                frameLayout.addView(nativeAdView);
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                // We recommend retrying with exponentially higher delays up to a maximum delay
                frameLayout.setVisibility(View.GONE);
            }


        });

        nativeAdLoader.loadAd();
    }

    // Shared Preference Local Data Get //
    public static String getSharedPreferenceData(Activity context, String prefName, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, 0);
        String data = sharedPreferences.getString(key, defaultValue);
        return data;
    }
    public static int getSharedPreferenceStatus(Activity context, String prefName, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, 0);
        int data = sharedPreferences.getInt(key, defaultValue);
        return data;
    }
    //    // Loader alert dialog //
    //    // Loader alert dialog //
    public static AlertDialog createLoaderAlertDialog(Activity activity) {
        //Custom layout set
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.ad_loder, null);

        //Alert dialog
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(view)
                .setCancelable(false)
                .create();
        alertDialog.show();
        return alertDialog;
    }

    // AD Adaptive banner //
    public static void adaptiveBannerAdSetup(Activity activity, Context context, String adId, MaterialCardView frameLayout) {
        MaxAdView adView;
        adView = new MaxAdView(adId, activity );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdExpended");
            }

            @Override
            public void onAdCollapsed(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdCollapsed");
            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdLoaded");
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdDisplayed");
                frameLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdHidden(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdHidden");
            }

            @Override
            public void onAdClicked(MaxAd ad) {
                Log.d("APP_LOVIN_TAG","onAdClicked");
            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                Log.d("APP_LOVIN_TAG","onAdFailed");
                frameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                Log.d("APP_LOVIN_TAG","onAdDisplayedFailed");
                frameLayout.setVisibility(View.GONE);
            }
        });

        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;

        // Get the adaptive banner height.
        int heightDp = MaxAdFormat.BANNER.getAdaptiveSize( activity ).getHeight();
        int heightPx = AppLovinSdkUtils.dpToPx( activity, heightDp );

        adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
        adView.setExtraParameter( "adaptive_banner", "true" );

        frameLayout.addView( adView );

        // Load the ad
        adView.loadAd();
    }
}
