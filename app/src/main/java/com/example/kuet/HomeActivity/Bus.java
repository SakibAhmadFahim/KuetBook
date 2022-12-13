package com.example.kuet.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.kuet.R;
import com.example.kuet.department.CustomWebViewClientaehCSE;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ortiz.touchview.TouchImageView;

import java.util.Objects;

public class Bus extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        FloatingActionButton floatingActionButton,floatingActionButton2,web;
        TouchImageView bus1,bus2;

        bus1=findViewById(R.id.bus1);
        bus2=findViewById(R.id.bus2);
        web=findViewById(R.id.web);

        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton2=findViewById(R.id.fab2);

        floatingActionButton.setOnClickListener(v -> {

            bus1.setVisibility(View.GONE);
            bus2.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.GONE);
            floatingActionButton2.setVisibility(View.VISIBLE);

        });
        web.setOnClickListener(v -> {

           startActivity(new Intent(Bus.this,busweb.class));

        });
        floatingActionButton2.setOnClickListener(v -> {

            bus2.setVisibility(View.GONE);
            bus1.setVisibility(View.VISIBLE);
            floatingActionButton2.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.VISIBLE);

        });


        /*String url =getIntent().getStringExtra("pdf_url");*/

        Objects.requireNonNull(getSupportActionBar()).setTitle("Bus Schedule");


       /* WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setSupportZoom(true);
        webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);*/
    }}