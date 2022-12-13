package com.example.kuet.HomeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kuet.CustomExpandableListAdapter;
import com.example.kuet.ExpandableListDataPump;
import com.example.kuet.department.ME;
import com.example.kuet.department.MSE;
import com.example.kuet.department.MTE;
import com.example.kuet.R;
import com.example.kuet.department.TE;
import com.example.kuet.department.URP;
import com.example.kuet.department.ARCH;
import com.example.kuet.department.BECM;
import com.example.kuet.department.BME;
import com.example.kuet.department.CSE;
import com.example.kuet.department.Chemical;
import com.example.kuet.department.Civil;
import com.example.kuet.department.ECE;
import com.example.kuet.department.EEE;
import com.example.kuet.department.ESE;
import com.example.kuet.department.IEM;
import com.example.kuet.department.LE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Department extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
       // expandableListView.setOnGroupExpandListener(groupPosition -> Toast.makeText(getApplicationContext(),
           //     expandableListTitle.get(groupPosition) + " List Expanded.",
          //      Toast.LENGTH_SHORT).show());

      //  expandableListView.setOnGroupCollapseListener(groupPosition -> Toast.makeText(getApplicationContext(),
                //expandableListTitle.get(groupPosition) + " List Collapsed.",
                //Toast.LENGTH_SHORT).show());

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            if(groupPosition==0){
                if(childPosition==0){
                    startActivity(new Intent(Department.this, EEE.class));
                }
            else if(childPosition==1){
                    startActivity(new Intent(Department.this, CSE.class));
                }
            else if(childPosition==2){
                    startActivity(new Intent(Department.this, ECE.class));
                }
            else if(childPosition==3){
                    startActivity(new Intent(Department.this, BME.class));
                }
            else if(childPosition==4){
                    startActivity(new Intent(Department.this, MSE.class));
                }
            }
            else if(groupPosition==1) {
                if (childPosition == 0) {
                    startActivity(new Intent(Department.this, ME.class));
                }
                else if (childPosition == 1) {
                    startActivity(new Intent(Department.this, IEM.class));
                }
                else if (childPosition == 2) {
                    startActivity(new Intent(Department.this, LE.class));
                }
                else if (childPosition == 3) {
                    startActivity(new Intent(Department.this, TE.class));
                }
                else if (childPosition == 4) {
                    startActivity(new Intent(Department.this, ESE.class));
                }
                else if (childPosition == 5) {
                    startActivity(new Intent(Department.this, Chemical.class));
                }
                else if (childPosition == 6) {
                    startActivity(new Intent(Department.this, MTE.class));
                }
            }
            else if(groupPosition==2) {
                if (childPosition == 0) {
                    startActivity(new Intent(Department.this, Civil.class));
                } else if (childPosition == 1) {
                    startActivity(new Intent(Department.this, URP.class));
                } else if (childPosition == 2) {
                    startActivity(new Intent(Department.this, BECM.class));
                } else if (childPosition == 3) {
                    startActivity(new Intent(Department.this, ARCH.class));
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
