package com.example.kuet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Profile extends AppCompatActivity {

    LinearLayout myprofile,about,contact,hall,automation,website,teacher,academic,signout,bus,library,department;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



    }
}



