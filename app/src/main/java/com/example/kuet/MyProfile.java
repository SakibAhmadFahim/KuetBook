package com.example.kuet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyProfile extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    TextView Name,Roll,Department,Hall,DOB,College,BG,Mobile,Home,Email;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Name = findViewById(R.id.academicname);
        Roll = findViewById(R.id.roll);
        Department = findViewById(R.id.dept);
        Hall = findViewById(R.id.hall);
        Home = findViewById(R.id.hometown);
        College = findViewById(R.id.college);
        DOB = findViewById(R.id.birthday);
        BG = findViewById(R.id.bg);
        Mobile = findViewById(R.id.mobile);
        Email = findViewById(R.id.email);
        //final MaterialButton Register = findViewById(R.id.register);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Registered Students");
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();

        // Read from the database
        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String txtName = snapshot.child("Name").getValue(String.class);
                String txtRoll = snapshot.child("Roll").getValue(String.class);
                String txtBG = snapshot.child("BG").getValue(String.class);
                String txtDepartment = snapshot.child("Department").getValue(String.class);
                String txtCollege = snapshot.child("College").getValue(String.class);
                String txtHomeTown = snapshot.child("Hometown").getValue(String.class);
                String txtHall = snapshot.child("Hall").getValue(String.class);
                String txtMobile = snapshot.child("Mobile").getValue(String.class);
                String txtdob = snapshot.child("DOB").getValue(String.class);
                // Log.d(TAG, "Value is: " + value);

                Name.setText(txtName);
                //Toast.makeText(this, Name.getText(), Toast.LENGTH_SHORT).show();
                Roll.setText(txtRoll);
                Department.setText(txtDepartment);
                Hall.setText(txtHall);
                Home.setText(txtHomeTown);
                Mobile.setText(txtMobile);
                DOB.setText(txtdob);
                College.setText(txtCollege);
                BG.setText(txtBG);
                //Email.setText(txtEmail);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}