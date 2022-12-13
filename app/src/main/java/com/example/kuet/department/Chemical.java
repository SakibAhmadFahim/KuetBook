package com.example.kuet.department;

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

public class Chemical extends AppCompatActivity {

    private WebView webView;
    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chemical);

        Objects.requireNonNull(getSupportActionBar()).hide();

        CustomWebViewClientaehChemical client=new CustomWebViewClientaehChemical(this);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.kuet.ac.bd/department/Chemical/");
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

class CustomWebViewClientaehChemical extends WebViewClient {

    private Activity activity;

    public CustomWebViewClientaehChemical(Activity activity){
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