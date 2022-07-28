package com.fnf.game.ugh.pack.more.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fnf.game.ugh.pack.more.R;
import com.fnf.game.ugh.pack.more.databinding.ActivityWebBinding;
import com.fnf.game.ugh.pack.more.util.NetworkUtil;

public class WebActivity extends AppCompatActivity {
        private ActivityWebBinding binding;
        private Context context;
        private Activity activity;
        private static final String url = "https://fnf.run3.io/ugh-pack/2/";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            NetworkUtil.hideStatusbar(WebActivity.this);
            binding = ActivityWebBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            context = getApplicationContext();
            activity = WebActivity.this;

            initUi();
        }

        // Init UI //
        private void initUi(){
            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.setWebChromeClient(new WebChromeClient());
            binding.webView.getSettings().setDomStorageEnabled(true);
            binding.webView.getSettings().setUseWideViewPort(true);
            binding.webView.setInitialScale(30);
            binding.webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view,url);
                    // do your stuff here
                    binding.webView.loadUrl("javascript:(function() { " +
                            "document.getElementById('playmore').style.display='none'; " +
                            "})()");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.webLoader.setVisibility(View.GONE);
                            binding.webView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            });
            binding.webView.loadUrl(url);
        }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            NetworkUtil.openNewAdActivity(this);
        }
    }