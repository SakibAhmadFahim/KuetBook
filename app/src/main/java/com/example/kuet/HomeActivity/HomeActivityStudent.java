package com.example.kuet.HomeActivity;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kuet.AllStudents;
import com.example.kuet.LoginStudent;
import com.example.kuet.MyProfile;
import com.example.kuet.R;
import com.example.kuet.ScheduleAlarm.MainActivity;
import com.example.kuet.ShowPostActivity;
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
import com.example.kuet.department.ME;
import com.example.kuet.department.MSE;
import com.example.kuet.department.MTE;
import com.example.kuet.department.TE;
import com.example.kuet.department.URP;
import com.example.kuet.hall.AEH;
import com.example.kuet.hall.ARH;
import com.example.kuet.hall.BBH;
import com.example.kuet.hall.FazlulHaqueHall;
import com.example.kuet.hall.KJAH;
import com.example.kuet.hall.LalanShah;
import com.example.kuet.hall.RokeyaHall;
import com.example.kuet.syllabus.SyllabusArch;
import com.example.kuet.syllabus.SyllabusBECM;
import com.example.kuet.syllabus.SyllabusBME;
import com.example.kuet.syllabus.SyllabusCSE;
import com.example.kuet.syllabus.SyllabusChemical;
import com.example.kuet.syllabus.SyllabusCivil;
import com.example.kuet.syllabus.SyllabusECE;
import com.example.kuet.syllabus.SyllabusEEE;
import com.example.kuet.syllabus.SyllabusESE;
import com.example.kuet.syllabus.SyllabusIEM;
import com.example.kuet.syllabus.SyllabusLE;
import com.example.kuet.syllabus.SyllabusME;
import com.example.kuet.syllabus.SyllabusMSE;
import com.example.kuet.syllabus.SyllabusMTE;
import com.example.kuet.syllabus.SyllabusTE;
import com.example.kuet.syllabus.SyllabusURP;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class HomeActivityStudent extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    LinearLayout ClassTest,Students,GitHub,Classmate,fb,drive,settings,calculator,about,schedule,contact,hall,automation,website,teacher,academic,signout,bus,library,department,Courses,myprofile;
    ImageView profile;
    String txtDepartment,txtHall,txtName,txtRoll,txtDepartmentRoll,txtFB,txtDrive,txtGitHub;
    TextView Name,Department,Roll;
    FirebaseUser firebaseUser;
    String userID;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Do you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        Objects.requireNonNull(getSupportActionBar()).hide();

        Name =findViewById(R.id.name);
        Roll =findViewById(R.id.roll);
        Department=findViewById(R.id.dept);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        assert firebaseUser != null;
        userID = firebaseUser.getUid();

        settings = findViewById(R.id.settings);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        settings.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Settings.class);
            startActivity(intent);
        });


        // Read from the database
        myRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtName = snapshot.child("Name").getValue(String.class);
                txtRoll = snapshot.child("Roll").getValue(String.class);
                txtDepartment = snapshot.child("Department").getValue(String.class);
                txtHall = snapshot.child("Hall").getValue(String.class);
                txtDepartmentRoll = txtDepartment +" 2K"+ txtRoll.charAt(0)+txtRoll.charAt(1);
                txtFB = snapshot.child("FB").getValue(String.class);
                txtDrive = snapshot.child("Drive").getValue(String.class);
                txtGitHub = snapshot.child("GitHub").getValue(String.class);


                // Log.d(TAG, "Value is: " + value);

                Name.setText(txtName);
                Roll.setText(txtRoll);
                Department.setText(txtDepartmentRoll);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    Students = findViewById(R.id.students);
        Students.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, AllStudents.class);
            startActivity(intent);
            finish();
        });

        ClassTest=findViewById(R.id.ct);
        ClassTest.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, ShowPostActivity.class);
            startActivity(intent);
            finish();
        });





        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

       /* GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){

        }
*/
        // initialize profile
        // with method findViewById()
        schedule = findViewById(R.id.Schedule);

        fb=findViewById(R.id.fb);
        fb.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else if(TextUtils.isEmpty(txtFB))
            {
                Toast.makeText(this, "Please set facebook group link in settings", Toast.LENGTH_SHORT).show();
            }
            else
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            {Intent intent = new Intent(HomeActivityStudent.this, FB_Group.class);
            startActivity(intent);
        }});

        GitHub=findViewById(R.id.github);
        GitHub.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else if(TextUtils.isEmpty(txtGitHub))
            {
                Toast.makeText(this, "Please set github account link in settings", Toast.LENGTH_SHORT).show();
            }
            else
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            {Intent intent = new Intent(HomeActivityStudent.this, GitHub.class);
                startActivity(intent);
            }});
        drive=findViewById(R.id.drive);

        drive.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else if(TextUtils.isEmpty(txtDrive))
            {
                Toast.makeText(this, "Please set google drive link in settings", Toast.LENGTH_SHORT).show();
            }
            else
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            {Intent intent = new Intent(HomeActivityStudent.this, GitHub.class);
            startActivity(intent);
        }});

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
       schedule.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Schedule.class);
            startActivity(intent);
        });
        automation = findViewById(R.id.automation);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
       automation.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Automation.class);
            startActivity(intent);
        });
       /* about = findViewById(R.id.about);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        about.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, About.class);

            startActivity(intent);
        });*/
        contact = findViewById(R.id.contact);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        contact.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, ContactUs.class);
            startActivity(intent);
        });
        department = findViewById(R.id.department);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        department.setOnClickListener(v -> {
            if(isNetworkAvailable() ||txtDepartment==null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                         .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                          .create().show();

            }
            else{

            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
                switch (txtDepartment) {
                    case "EEE": {
                        Intent intent = new Intent(HomeActivityStudent.this, EEE.class);
                        startActivity(intent);
                        break;
                    }
                    case "CSE": {
                        Intent intent = new Intent(HomeActivityStudent.this, CSE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ECE": {
                        Intent intent = new Intent(HomeActivityStudent.this, ECE.class);
                        startActivity(intent);
                        break;
                    }
                    case "BME": {
                        Intent intent = new Intent(HomeActivityStudent.this, BME.class);
                        startActivity(intent);
                        break;
                    }
                    case "MSE": {
                        Intent intent = new Intent(HomeActivityStudent.this, MSE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ME": {
                        Intent intent = new Intent(HomeActivityStudent.this, ME.class);
                        startActivity(intent);
                        break;
                    }
                    case "IEM": {
                        Intent intent = new Intent(HomeActivityStudent.this, IEM.class);
                        startActivity(intent);
                        break;
                    }
                    case "ESE": {
                        Intent intent = new Intent(HomeActivityStudent.this, ESE.class);
                        startActivity(intent);
                        break;
                    }
                    case "LE": {
                        Intent intent = new Intent(HomeActivityStudent.this, LE.class);
                        startActivity(intent);
                        break;
                    }
                    case "TE": {
                        Intent intent = new Intent(HomeActivityStudent.this, TE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ChE": {
                        Intent intent = new Intent(HomeActivityStudent.this, Chemical.class);
                        startActivity(intent);
                        break;
                    }
                    case "MTE": {
                        Intent intent = new Intent(HomeActivityStudent.this, MTE.class);
                        startActivity(intent);
                        break;
                    }
                    case "CE": {
                        Intent intent = new Intent(HomeActivityStudent.this, Civil.class);
                        startActivity(intent);
                        break;
                    }
                    case "URP": {
                        Intent intent = new Intent(HomeActivityStudent.this, URP.class);
                        startActivity(intent);
                        break;
                    }
                    case "BECM": {
                        Intent intent = new Intent(HomeActivityStudent.this, BECM.class);
                        startActivity(intent);
                        break;
                    }
                    case "Arch.": {
                        Intent intent = new Intent(HomeActivityStudent.this, ARCH.class);
                        startActivity(intent);
                        break;
                    }
                }

            Toast.makeText(this, "Welcome to Department of " + txtDepartment, Toast.LENGTH_SHORT).show();
        }});

        teacher=findViewById(R.id.teacher);

        teacher.setOnClickListener(v -> {
            if(isNetworkAvailable() ||txtDepartment==null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{

                // Intent class will help to go to next activity using
                // it's object named intent.
                // SecondActivity is the name of new created EmptyActivity.
                switch (txtDepartment) {
                    case "EEE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.EEE.class);
                        startActivity(intent);
                        break;
                    }
                    case "CSE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.CSE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ECE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.ECE.class);
                        startActivity(intent);
                        break;
                    }
                    case "BME": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.BME.class);
                        startActivity(intent);
                        break;
                    }
                    case "MSE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.MSE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ME": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.ME.class);
                        startActivity(intent);
                        break;
                    }
                    case "IEM": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.IEM.class);
                        startActivity(intent);
                        break;
                    }
                    case "ESE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.ESE.class);
                        startActivity(intent);
                        break;
                    }
                    case "LE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.LE.class);
                        startActivity(intent);
                        break;
                    }
                    case "TE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.TE.class);
                        startActivity(intent);
                        break;
                    }
                    case "ChE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.ChE.class);
                        startActivity(intent);
                        break;
                    }
                    case "MTE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.MTE.class);
                        startActivity(intent);
                        break;
                    }
                    case "CE": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.CE.class);
                        startActivity(intent);
                        break;
                    }
                    case "URP": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.URP.class);
                        startActivity(intent);
                        break;
                    }
                    case "BECM": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.BECM.class);
                        startActivity(intent);
                        break;
                    }
                    case "Arch.": {
                        Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.teacher.ARCH.class);
                        startActivity(intent);
                        break;
                    }
                }

                //Toast.makeText(this, "Welcome to Department of " + txtDepartment, Toast.LENGTH_SHORT).show();
            }});

        Courses = findViewById(R.id.courses);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        Courses.setOnClickListener(v -> {

            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }

            else{

            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            if(Objects.equals(txtDepartment, "EEE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusEEE.class);
                startActivity(intent);
            }

            else if(Objects.equals(txtDepartment, "CSE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusCSE.class);
                startActivity(intent);

            }
            else if(Objects.equals(txtDepartment, "ECE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusECE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "BME")){
                Intent intent = new Intent(HomeActivityStudent.this,  SyllabusBME.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "MSE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusMSE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "ME")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusME.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "IEM")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusIEM.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "ESE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusESE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "LE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusLE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "TE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusTE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "ChE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusChemical.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "MTE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusMTE.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "CE")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusCivil.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "URP")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusURP.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtDepartment, "BECM")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusBECM.class);
                startActivity(intent);
            }

            else if(Objects.equals(txtDepartment, "Arch.")){
                Intent intent = new Intent(HomeActivityStudent.this, SyllabusArch.class);
                startActivity(intent);
            }
           // Toast.makeText(this, "Welcome to Department of " + txtDepartment, Toast.LENGTH_SHORT).show();
        }});
        // initialize hall
        // with method findViewById()
        hall = findViewById(R.id.hall);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        hall.setOnClickListener(v -> {
            if(isNetworkAvailable() ||txtHall==null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{


                switch (txtHall) {
                    case "Bangabandhu Sheikh Mujibur Rahman Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, BBH.class);
                        startActivity(intent);
                        break;
                    }
                    case "Fazlul Haque Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, FazlulHaqueHall.class);
                        startActivity(intent);
                        break;
                    }
                    case "Lalan Shah Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, LalanShah.class);
                        startActivity(intent);
                        break;
                    }
                    case "Khan Jahan Ali Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, KJAH.class);
                        startActivity(intent);
                        break;
                    }
                    case "Rokeya Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, RokeyaHall.class);
                        startActivity(intent);
                        break;
                    }
                    case "Dr. M.A. Rashid Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, ARH.class);
                        startActivity(intent);
                        break;
                    }
                    case "Amar Ekushey Hall": {
                        Intent intent = new Intent(HomeActivityStudent.this, AEH.class);
                        startActivity(intent);
                        break;
                    }
                }
            Toast.makeText(this, "Welcome to " + txtHall, Toast.LENGTH_SHORT).show();

        }});

        // initialize library
        // with method findViewById()
        library = findViewById(R.id.library);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        library.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Library.class);
            startActivity(intent);
        }});
        calculator = findViewById(R.id.calculator);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        calculator.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Calculator.class);
            startActivity(intent);
        });

        // initialize SignOut
        // with method findViewById()
        signout = findViewById(R.id.signout);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        signout.setOnClickListener(v -> signout());

        // initialize automation
        // with method findViewById()
        automation = findViewById(R.id.automation);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        automation.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Automation.class);
            startActivity(intent);
        });

        // initialize academic
        // with method findViewById()
        academic = findViewById(R.id.academic);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        academic.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{

            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, AcademicUG.class);
            startActivity(intent);
        }});

        // initialize bus
        // with method findViewById()
         bus = findViewById(R.id.bus);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        bus.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{
            Intent intent = new Intent(HomeActivityStudent.this, Bus.class);
            intent.putExtra("pdf_url","https://www.kuet.ac.bd/transport_notice/busSchedule_29_08_2022.pdf");
            startActivity(intent);}
        });

        // initialize website
        // with method findViewById()
        website = findViewById(R.id.website);


        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        website.setOnClickListener(v -> {
            if(isNetworkAvailable())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Internet Connection Alert")
                        .setMessage("Please Check Your Internet Connection")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create().show();

            }
            else{// Intent class will help to go to next activity using

            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Website.class);
            startActivity(intent);
        }});

        // initialize teacher
        // with method findViewById()
       /* teacher = findViewById(R.id.teacher);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        teacher.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Teacher.class);
            startActivity(intent);
        });*/

        myprofile = findViewById(R.id.my_profile);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        myprofile.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, MyProfile.class);
            startActivity(intent);
            finish();
        });

        Classmate = findViewById(R.id.classmate);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        Classmate.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, com.example.kuet.HomeActivity.Classmate.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return false;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return false;
                    } else return !capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            }
        }

        return true;

    }


    private void signout() {
            mAuth=FirebaseAuth.getInstance();
            mAuth.signOut();

            mGoogleSignInClient.signOut();


            Intent intent =new Intent(HomeActivityStudent.this, LoginStudent.class);
            intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK) | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
    }



}

