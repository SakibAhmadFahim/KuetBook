package com.example.kuet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AcademicUG extends AppCompatActivity {

    private WebView webView;
    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_ug);

        CustomWebViewClientaehAUG client=new CustomWebViewClientaehAUG(this);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://academic.kuet.ac.bd/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()){
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}

class CustomWebViewClientaehAUG extends WebViewClient {

    private Activity activity;

    public CustomWebViewClientaehAUG(Activity activity){
        this.activity=activity;
    }

    ///API Level less than 24
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    ///API Level >=24
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
    }
}