package com.example.kuet.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kuet.R;

import java.util.Objects;

public class Course extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}