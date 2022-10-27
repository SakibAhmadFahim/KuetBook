package com.example.kuet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Automation extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automation);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
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
