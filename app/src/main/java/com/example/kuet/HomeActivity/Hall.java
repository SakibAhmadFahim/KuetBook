package com.example.kuet.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuet.hall.LalanShah;
import com.example.kuet.R;
import com.example.kuet.hall.RokeyaHall;
import com.example.kuet.hall.AEH;
import com.example.kuet.hall.ARH;
import com.example.kuet.hall.BBH;
import com.example.kuet.hall.FazlulHaqueHall;
import com.example.kuet.hall.KJAH;

import java.util.ArrayList;
import java.util.List;

public class Hall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);


        ListView listView = findViewById(R.id.listview);

        List<String> list = new ArrayList<>();
        list.add(">  Fazlul Haque Hall");
        list.add(">  Lalan Shah Hall");
        list.add(">  Khan Jahan Ali Hall");
        list.add(">  Dr. M.A. Rashid Hall");
        list.add(">  Rokeya Hall");
        list.add(">  Amar Ekushey Hall");
        list.add(">  Bangabandhu Sheikh Mujibur Rahman Hall");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.hall,list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            if(position==0){
                //clicked apple

                startActivity(new Intent(Hall.this, FazlulHaqueHall.class));

            }else if(position==1){
                //clicked orange
                startActivity(new Intent(Hall.this, LalanShah.class));
            }
            else if(position==2){
                //clicked orange
                startActivity(new Intent(Hall.this, KJAH.class));
            }
            else if(position==3){
                //clicked orange
                startActivity(new Intent(Hall.this, ARH.class));
            }
            else if(position==4){
                //clicked orange
                startActivity(new Intent(Hall.this, RokeyaHall.class));
            }
            else if(position==5){
                //clicked orange
                startActivity(new Intent(Hall.this, AEH.class));
            }
            else if(position==6){
                //clicked orange
                startActivity(new Intent(Hall.this, BBH.class));
            }
        });


    }
}