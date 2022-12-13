package com.example.kuet.hall;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kuet.R;

import java.util.Objects;

public class BBH extends AppCompatActivity {

    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbh);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Hall");


        CustomWebViewClientBBH client=new CustomWebViewClientBBH(this);
        webView= findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://hall.kuet.ac.bd/bbh/");
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

class CustomWebViewClientBBH extends WebViewClient {

    private Activity activity;

    public CustomWebViewClientBBH(Activity activity){
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