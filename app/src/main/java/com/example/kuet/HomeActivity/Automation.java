package com.example.kuet.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuet.AcademicPG;
import com.example.kuet.Alumni;
import com.example.kuet.CustomExpandableListAdapter;
import com.example.kuet.ExpandableListDataPump2;
import com.example.kuet.hall.FazlulHaqueHall;
import com.example.kuet.GraduatePortal;
import com.example.kuet.hall.LalanShah;
import com.example.kuet.OnlinePaymentService;
import com.example.kuet.R;
import com.example.kuet.hall.RokeyaHall;
import com.example.kuet.VehicleRequisitionSystem;
import com.example.kuet.hall.ARH;
import com.example.kuet.hall.BBH;
import com.example.kuet.hall.KJAH;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Automation extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Automation");
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump2.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
       // expandableListView.setOnGroupExpandListener(groupPosition -> Toast.makeText(getApplicationContext(),
          //      expandableListTitle.get(groupPosition) + " List Expanded.",
          //      Toast.LENGTH_SHORT).show());

      //  expandableListView.setOnGroupCollapseListener(groupPosition -> Toast.makeText(getApplicationContext(),
        //        expandableListTitle.get(groupPosition) + " List Collapsed.",
        //        Toast.LENGTH_SHORT).show());

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            if(groupPosition==0) {
                if (childPosition == 0) {
                    startActivity(new Intent(Automation.this, OnlinePaymentService.class));
                }
            }

            else if(groupPosition==1) {
                if (childPosition == 0) {
                    startActivity(new Intent(Automation.this, AcademicUG.class));
                } else if (childPosition == 1) {
                    startActivity(new Intent(Automation.this, AcademicPG.class));
                } else if (childPosition == 2) {
                    startActivity(new Intent(Automation.this, GraduatePortal.class));
                } else if (childPosition == 3) {
                    startActivity(new Intent(Automation.this, Library.class));
                }
            }

            else if(groupPosition==2) {
                if (childPosition == 0) {
                    startActivity(new Intent(Automation.this, Alumni.class));

                }
            }

            else if(groupPosition==3) {
                if (childPosition == 0) {
                    startActivity(new Intent(Automation.this, VehicleRequisitionSystem.class));
                }
            }


            else if(groupPosition==4) {
                if (childPosition == 0) {
                    startActivity(new Intent(Automation.this, FazlulHaqueHall.class));
                } else if (childPosition == 1) {
                    startActivity(new Intent(Automation.this, LalanShah.class));
                } else if (childPosition == 2) {
                    startActivity(new Intent(Automation.this, KJAH.class));
                } else if (childPosition == 3) {
                    startActivity(new Intent(Automation.this, ARH.class));
                }
                else if (childPosition == 4) {
                    startActivity(new Intent(Automation.this, RokeyaHall.class));
                }
                else if (childPosition == 5) {
                    startActivity(new Intent(Automation.this, ARH.class));
                }
                else if (childPosition == 6) {
                    startActivity(new Intent(Automation.this, BBH.class));
                }
            }


            // Toast.makeText(
            //          getApplicationContext(),
            //          expandableListTitle.get(groupPosition)
            //                 + " -> "
            //                 + Objects.requireNonNull(expandableListDetail.get(
            //                expandableListTitle.get(groupPosition))).get(
            //                 childPosition), Toast.LENGTH_SHORT
            // ).show();
            return false;
        });
    }

}
