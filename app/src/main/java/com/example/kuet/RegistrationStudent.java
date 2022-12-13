package com.example.kuet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationStudent extends AppCompatActivity {

    private static final String TAG="RegistrationStudent";

   // DatabaseHelper mDatabaseHelper;
    ProgressDialog progressDialog;
    RelativeLayout layout1,layout2;
    private DatePickerDialog picker;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String myUid;
    @Override
    public void onBackPressed() {
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        if(layout2.getVisibility()==View.VISIBLE){
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create New Account");

     /*   ActionBar actionBar = getActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        setContentView(R.layout.activity_registration_student);

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


        MaterialButton next = findViewById(R.id.next);
        final EditText Name = findViewById(R.id.academicname);
        final EditText Roll = findViewById(R.id.roll);
        final EditText Home = findViewById(R.id.hometown);
        final EditText College = findViewById(R.id.college);
        final TextView DOB = findViewById(R.id.birthday);
        final EditText BG = findViewById(R.id.bg);
        final EditText Mobile = findViewById(R.id.mobile);
        final EditText Email = findViewById(R.id.email);
        final EditText Password = findViewById(R.id.pass);
        final EditText ConfirmPassword = findViewById(R.id.confirmpass);
        final MaterialButton Register = findViewById(R.id.register);
        final TextView signin = findViewById(R.id.signin);
        final TextView login = findViewById(R.id.login);

        // mDatabaseHelper=new DatabaseHelper(this);

        DOB.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(RegistrationStudent.this, (view, year1, month1, dayOfMonth) -> DOB.setText((dayOfMonth + "/" + (month1 + 1) + "/" + year1)), year, month, day);
            picker.show();
        });

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        next.setOnClickListener(view -> {

            final String txtMobile = Mobile.getText().toString();
            final String txtName = Name.getText().toString();
            final String txtRoll = Roll.getText().toString();
            final String txtHome = Home.getText().toString();
            final String txtCollege = College.getText().toString();
            final String txtdoB = DOB.getText().toString();
            final String txtBG = BG.getText().toString();

            String mobileRegex = "^$|([0][1][0-9]{9})";
            Matcher mobileMatcher;
            Pattern mobilePattern = Pattern.compile(mobileRegex);
            mobileMatcher = mobilePattern.matcher(txtMobile);

            String name = "^[a-zA-Z\\s]+";
            Matcher nameMatcher;
            Pattern namePattern = Pattern.compile(name);
            nameMatcher = namePattern.matcher(txtName);

            String roll = "[0-9]{7}";
            Matcher rollMatcher;
            Pattern rollPattern = Pattern.compile(roll);
            rollMatcher = rollPattern.matcher(txtRoll);

            if (!nameMatcher.find()) {
                //Name.setError("Name is not Valid!!");
                Toast.makeText(this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
                Name.requestFocus();
            } else if (!rollMatcher.find()) {
                //Roll.setError("Roll Number is not Valid!!");
                Toast.makeText(this, "Please enter a valid roll number", Toast.LENGTH_SHORT).show();
                Roll.requestFocus();
            } else if (!mobileMatcher.find()) {
                //Toast.makeText(Registration.this,"Password not matched",Toast.LENGTH_SHORT).show();
               // Mobile.setError("Mobile No. is not Valid!!");
                Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                Mobile.requestFocus();
            }

/*

            else if(PasswordTxt.isEmpty()){
                Toast.makeText(LoginStudent.this,"Please enter your password",Toast.LENGTH_SHORT).show();
            }
*/

            else {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        });


        signin.setPaintFlags(signin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        login.setPaintFlags(login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signin.setOnClickListener(view -> {
            Intent myIntent = new Intent(RegistrationStudent.this, LoginStudent.class);
            //myIntent.putExtra("key", value); //Optional parameters
            RegistrationStudent.this.startActivity(myIntent);
            finish();
        });

        login.setOnClickListener(view -> {
            Intent myIntent = new Intent(RegistrationStudent.this, LoginStudent.class);
            //myIntent.putExtra("key", value); //Optional parameters
            RegistrationStudent.this.startActivity(myIntent);
        });


        Register.setOnClickListener(v -> {




            final String txtEmail = Email.getText().toString();
            final String txtDepartment = spinner.getSelectedItem().toString();
            final String txtHall = spinner2.getSelectedItem().toString();
            final String PasswordTxt = Password.getText().toString();
            final String ConfirmPasswordTxt = ConfirmPassword.getText().toString();
            final String txtName = Name.getText().toString();
            final String txtRoll = Roll.getText().toString();
            final String txtHome = Home.getText().toString();
            final String txtCollege = College.getText().toString();
            final String txtdoB = DOB.getText().toString();
            final String txtBG = BG.getText().toString();
            final String txtMobile = Mobile.getText().toString();


            String EmailRegex = "[a-z]+[0-9]{7}[@][s][t][u][d][.][k][u][e][t][.][a][c][.][b][d]";
            Matcher emailMatcher;
            Pattern emailPattern = Pattern.compile(EmailRegex);
            emailMatcher = emailPattern.matcher(txtEmail);

            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                //Email.setError("Email is invalid");
                Toast.makeText(RegistrationStudent.this, "Email is invalid", Toast.LENGTH_SHORT).show();
            }
          /*  else if(!emailMatcher.find()){
                Toast.makeText(RegistrationStudent.this,"Please Enter your KUET Email",Toast.LENGTH_SHORT).show();
            }*/

            else if (PasswordTxt.length() < 8) {
                //Password.setError("Password length is invalid");
                Toast.makeText(RegistrationStudent.this, "Password length is invalid", Toast.LENGTH_SHORT).show();
            } else if (!PasswordTxt.equals(ConfirmPasswordTxt)) {
                //ConfirmPassword.setError("Password not matched");
                Toast.makeText(RegistrationStudent.this, "Password not matched", Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(txtEmail, PasswordTxt).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        firebaseAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        assert user != null;
                        myUid = user.getUid();

                        //UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(txtName).build();

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("Name", txtName);
                        hashMap.put("Roll", txtRoll);
                        hashMap.put("Department", txtDepartment);
                        hashMap.put("Hall", txtHall);
                        hashMap.put("Home", txtHome);
                        hashMap.put("College", txtCollege);
                        hashMap.put("uid", myUid);
                        hashMap.put("Birthday",txtdoB);
                        hashMap.put("Blood_Group", txtBG);
                        hashMap.put("onlineStatus", "online");
                        hashMap.put("typingTo", "noOne");
                        hashMap.put("Password", PasswordTxt);
                        hashMap.put("Email", txtEmail);
                        hashMap.put("Mobile", txtMobile);
                        hashMap.put("FB", "");
                        hashMap.put("Drive", "");
                        hashMap.put("GitHub","");

                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference reference=database.getReference("Users");
                        reference.child(myUid).setValue(hashMap);
                        //firebaseUser.updateProfile(profileChangeRequest);

                        //Enter User Data into the Firebase Realtime Database.
                        //ReadWriteUserDetails writeUserDetails=new ReadWriteUserDetails(txtName,txtRoll,txtDepartment,txtHall,txtHome,txtCollege,txtdoB,txtBG,txtMobile,txtEmail,PasswordTxt,OnlineStatus);


                                progressDialog.dismiss();
                                // Send verification Email
                                user.sendEmailVerification();
                                mAuth.signOut();
                                Toast.makeText(RegistrationStudent.this, "User registered successfully. Please verify your Email.", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(RegistrationStudent.this, LoginStudent.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();



                        }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationStudent.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }



                });

            }
        });
    }
}



