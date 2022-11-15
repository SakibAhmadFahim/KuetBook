package com.example.kuet;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {

    FirebaseDatabase database;

    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    ProgressDialog pd;
    private DatePickerDialog picker;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MyProfile.this, HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);



        MaterialButton Save = findViewById(R.id.save);
        final TextView Name = findViewById(R.id.academicname);
        final TextView Roll = findViewById(R.id.roll);
        final TextView Home = findViewById(R.id.hometown);
        final TextView College = findViewById(R.id.college);
        final TextView dob = findViewById(R.id.birthday);
        final TextView BG = findViewById(R.id.bg);
        final TextView Mobile = findViewById(R.id.mobile);

        Spinner spinner = findViewById(R.id.dept_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments, R.layout.spinneritem);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Spinner spinner2 = findViewById(R.id.hall_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.hall, R.layout.spinneritem);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        /*Email = findViewById(R.id.email);*/
        //final MaterialButton Register = findViewById(R.id.register);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Registered Students");
        assert firebaseUser != null;
        String userID = firebaseUser.getUid();
        pd = new ProgressDialog(MyProfile.this);

        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String txtName = snapshot.child("Name").getValue(String.class);
                String txtRoll = snapshot.child("Roll").getValue(String.class);
                String txtBG = snapshot.child("BG").getValue(String.class);
                String txtDepartment = snapshot.child("Department").getValue(String.class);
                String txtCollege = snapshot.child("College").getValue(String.class);
                String txtHomeTown = snapshot.child("HomeTown").getValue(String.class);
                String txtHall = snapshot.child("Hall").getValue(String.class);
                String txtMobile = snapshot.child("Mobile").getValue(String.class);
                String txtdob = snapshot.child("DOB").getValue(String.class);
                // Log.d(TAG, "Value is: " + value);

                int spinnerPosition = adapter.getPosition(txtDepartment);
                int spinnerPosition2 = adapter2.getPosition(txtHall);

                Name.setText(txtName);
                //Toast.makeText(this, Name.getText(), Toast.LENGTH_SHORT).show();
                Roll.setText(txtRoll);
                spinner.setSelection(spinnerPosition);
                spinner2.setSelection(spinnerPosition2);
                Home.setText(txtHomeTown);
                Mobile.setText(txtMobile);
                dob.setText(txtdob);
                College.setText(txtCollege);
                BG.setText(txtBG);
                //Email.setText(txtEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

       String Dept=spinner.getSelectedItem().toString();
       String Hll = spinner2.getSelectedItem().toString();

      /* Toast.makeText(MyProfile.this,""+Dept,Toast.LENGTH_SHORT).show();

        Map<String, Object> department = new HashMap<>();
        department.put("Department", Dept);

        myRef.child(userID).updateChildren(department);

        Map<String, Object> hall = new HashMap<>();
        hall.put("Hall", Hll);

        myRef.child(userID).updateChildren(hall);*/




        Name.setOnClickListener(v -> {
            pd.setMessage("Updating Name");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update Name");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter Name");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    Name.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("Name", value);
                    Name.setText(value);
                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                }

            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();

        });

        Roll.setOnClickListener(v -> {
            pd.setMessage("Updating Roll");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update Roll No.");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter Roll No.");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    Roll.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("Roll", value);
                    Roll.setText(value);
                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your roll.", Toast.LENGTH_SHORT).show();
                }

            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });

        Home.setOnClickListener(v -> {
            pd.setMessage("Updating Home");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update Home");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter Home");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    Home.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("HomeTown", value);
                    Home.setText(value);
                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your hometown.", Toast.LENGTH_SHORT).show();
                }

            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
        College.setOnClickListener(v -> {
            pd.setMessage("Updating College");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update College");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter College");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    College.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("Name", value);
                    College.setText(value);
                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your college.", Toast.LENGTH_SHORT).show();
                }

            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
        dob.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(MyProfile.this, (view, year1, month1, dayOfMonth) -> dob.setText((dayOfMonth + "/" + (month1 + 1) + "/" + year1)), year, month, day);


            //Toast.makeText(MyProfile.this,""+DOB,Toast.LENGTH_SHORT).show();

            final String txtdoB = dob.getText().toString();

            Toast.makeText(MyProfile.this, "Please select date of birth", Toast.LENGTH_SHORT).show();

            Map<String, Object> result = new HashMap<>();
            result.put("DOB", txtdoB);

            myRef.child(userID).updateChildren(result);

            picker.show();


        });


        BG.setOnClickListener(v -> {
            pd.setMessage("Updating Blood Group");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update Blood Group");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter Blood Group");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    BG.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("BG", value);
                    BG.setText(value);

                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your blood group.", Toast.LENGTH_SHORT).show();
                }

            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });
        Mobile.setOnClickListener(v -> {
            pd.setMessage("Updating Mobile No.");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
            builder.setTitle("Update Mobile No.");

            LinearLayout linearLayout = new LinearLayout(MyProfile.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(10, 10, 10, 10);

            EditText editText = new EditText(MyProfile.this);
            editText.setHint("Enter Mobile No.");
            linearLayout.addView(editText);

            builder.setView(linearLayout);

            builder.setPositiveButton("Update", (dialog, which) -> {
                String value = editText.getText().toString().trim();

                if (!TextUtils.isEmpty(value)) {
                    Mobile.setText("");
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("Mobile", value);
                    Mobile.setText(value);

                    myRef.child(firebaseUser.getUid()).updateChildren(result)
                            .addOnSuccessListener(unused -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                            })
                            .addOnFailureListener(e -> {
                                pd.dismiss();
                                Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(MyProfile.this, "Please enter your mobile no.", Toast.LENGTH_SHORT).show();
                }


            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.create().show();
        });


        // Read from the database


        Save.setOnClickListener(v -> {
            Intent myIntent = new Intent(MyProfile.this, HomeActivityStudent.class);
            //myIntent.putExtra("key", value); //Optional parameters
            MyProfile.this.startActivity(myIntent);
            finish();
        });


    }
}
    /*private void showUpdateDialog(String key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Update "+key);

        LinearLayout linearLayout=new LinearLayout(MyProfile.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        EditText editText= new EditText(MyProfile.this);
        editText.setHint("Enter "+key);
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String value = editText.getText().toString().trim();

            if(!TextUtils.isEmpty(value)){
                pd.show();
                HashMap<String, Object>result = new HashMap<>();
                result.put(key,value);

                myRef.child(firebaseUser.getUid()).updateChildren(result)
                        .addOnSuccessListener(unused -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this,"Updated...",Toast.LENGTH_SHORT).show();



                        })
                        .addOnFailureListener(e -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        });
            }else{
                Toast.makeText(MyProfile.this,"Please Enter "+key,Toast.LENGTH_SHORT).show();
            }

        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();

    }
}*/