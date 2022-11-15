package com.example.kuet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.kuet.CustomWebViewClientaehCSE;
import com.example.kuet.R;

public class SyllabusCSE extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_civil);

        CustomWebViewClientaehCSE client=new CustomWebViewClientaehCSE(this);
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/CSE.html");
    }
}

