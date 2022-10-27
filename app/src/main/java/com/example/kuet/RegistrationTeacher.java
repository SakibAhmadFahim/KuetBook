package com.example.kuet;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationTeacher extends AppCompatActivity {

    ProgressBar progressBar;
    RelativeLayout layout1, layout2;
    private DatePickerDialog picker;

    @Override
    public void onBackPressed() {
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        if (layout2.getVisibility() == View.VISIBLE) {
            layout2.setVisibility(View.GONE);
            layout1.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registration_teacher);

        Spinner spinner = findViewById(R.id.dept_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments, R.layout.spinneritem);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        MaterialButton next = findViewById(R.id.next);
        final EditText Name = findViewById(R.id.academicname);
        final EditText Home = findViewById(R.id.hometown);
        final EditText DOB = findViewById(R.id.birthday);
        final EditText BG = findViewById(R.id.bg);
        final EditText Mobile = findViewById(R.id.mobile);
        final EditText Email = findViewById(R.id.email);
        final EditText Password = findViewById(R.id.pass);
        final EditText ConfirmPassword = findViewById(R.id.confirmpass);
        final MaterialButton Register = findViewById(R.id.register);
        final TextView signin = findViewById(R.id.signin);
        final TextView login = findViewById(R.id.login);

        DOB.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            picker = new DatePickerDialog(RegistrationTeacher.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    DOB.setText((dayOfMonth + "/" + (month + 1) + "/" + year));

                }
            }, year, month, day);
            picker.show();
        });

        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);

        next.setOnClickListener(view -> {

            final String txtMobile = Mobile.getText().toString();
            final String txtName = Name.getText().toString();

            final String txtHome = Home.getText().toString();

            final String txtdoB = DOB.getText().toString();
            final String txtBG = BG.getText().toString();
            final String txtEmail = Email.getText().toString();
            final String txtDepartment = spinner.getSelectedItem().toString();


//            String mobileRegex="[0][1][0-9]{9}";
//            Matcher mobileMatcher;
//            Pattern mobilePattern = Pattern.compile(mobileRegex);
//            mobileMatcher = mobilePattern.matcher(txtMobile);
//
//            if(!mobileMatcher.find()){
//                //Toast.makeText(Registration.this,"Password not matched",Toast.LENGTH_SHORT).show();
//                Mobile.setError("Mobile No. is not Valid!!");
//                Mobile.requestFocus();
//            }
//
//            else {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
//            }
        });


        signin.setPaintFlags(signin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        login.setPaintFlags(login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signin.setOnClickListener(view -> {
            Intent myIntent = new Intent(RegistrationTeacher.this, LoginTeacher.class);
            //myIntent.putExtra("key", value); //Optional parameters
            RegistrationTeacher.this.startActivity(myIntent);
        });

        login.setOnClickListener(view -> {
            Intent myIntent = new Intent(RegistrationTeacher.this, LoginTeacher.class);
            //myIntent.putExtra("key", value); //Optional parameters
            RegistrationTeacher.this.startActivity(myIntent);
        });


        Register.setOnClickListener(v -> {

            final String txtEmail = Email.getText().toString();
            final String txtDepartment = spinner.getSelectedItem().toString();
            final String PasswordTxt = Password.getText().toString();
            final String ConfirmPasswordTxt = ConfirmPassword.getText().toString();
            final String txtName = Name.getText().toString();

            final String txtHome = Home.getText().toString();

            final String txtdoB = DOB.getText().toString();
            final String txtBG = BG.getText().toString();
            final String txtMobile = Mobile.getText().toString();


            String EmailRegex = "[a-z]+[@][a-z]+[.][k][u][e][t][.][a][c][.][b][d]";
            Matcher emailMatcher;
            Pattern emailPattern = Pattern.compile(EmailRegex);
            emailMatcher = emailPattern.matcher(txtEmail);
            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                //Email.setError("Email is invalid");
                Toast.makeText(RegistrationTeacher.this, "Email is invalid", Toast.LENGTH_SHORT).show();
            } else if (!emailMatcher.find()) {
                Toast.makeText(RegistrationTeacher.this, "Please Enter your KUET Email", Toast.LENGTH_SHORT).show();
            } else if (PasswordTxt.length() < 8) {
                //Password.setError("Password length is invalid");
                Toast.makeText(RegistrationTeacher.this, "Password length is invalid", Toast.LENGTH_SHORT).show();
            } else if (!PasswordTxt.equals(ConfirmPasswordTxt)) {
                //ConfirmPassword.setError("Password not matched");
                Toast.makeText(RegistrationTeacher.this, "Password not matched", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(txtEmail, PasswordTxt).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(txtName).build();
                        assert firebaseUser != null;
                        firebaseUser.updateProfile(profileChangeRequest);

                        //Enter User Data into the Firebase Realtime Database.
                        ReadWriteTeacherDetails writeUserDetails = new ReadWriteTeacherDetails(txtName, txtDepartment, txtHome, txtdoB, txtBG, txtMobile, txtEmail, PasswordTxt);

                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Teachers");

                        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                // Send verification Email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegistrationTeacher.this, "User registered successfully. Please verify your Email.", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                                Intent intent = new Intent(RegistrationTeacher.this, LoginTeacher.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegistrationTeacher.this, "User registered failed. Please try again.", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        Toast.makeText(RegistrationTeacher.this, "Registration Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}



