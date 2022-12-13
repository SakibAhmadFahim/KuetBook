package com.example.kuet.syllabus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.kuet.R;
import com.example.kuet.department.CustomWebViewClientaehCSE;

import java.util.Objects;

public class SyllabusIEM extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus_iem);

        Objects.requireNonNull(getSupportActionBar()).hide();

        CustomWebViewClientaehCSE client=new CustomWebViewClientaehCSE(this);
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/IEM.html");
    }
}

