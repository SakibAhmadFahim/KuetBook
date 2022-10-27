package com.example.kuet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class LoginInAs extends AppCompatActivity {

    Button Button1,Button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in_as);

        Button1=findViewById(R.id.signinasstudent);
        Button2=findViewById(R.id.signinasteacher);

        Button1.setOnClickListener(v -> {
            Intent intent = new Intent(LoginInAs.this, LoginStudent.class);
            startActivity(intent);
            finish();
        });

        Button2.setOnClickListener(v -> {
            Intent intent = new Intent(LoginInAs.this, LoginTeacher.class);
            startActivity(intent);
            finish();
        });
    }
}