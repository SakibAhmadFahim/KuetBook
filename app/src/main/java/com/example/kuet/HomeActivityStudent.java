package com.example.kuet;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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


    LinearLayout calculator,about,contact,hall,automation,website,teacher,academic,signout,bus,library,department,myprofile;
    ImageView profile;
    String txtDepartment,txtHall;
    TextView Name,Roll,Department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_student);

        Name =findViewById(R.id.name);
        Department=findViewById(R.id.dept);
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
                txtDepartment = snapshot.child("Department").getValue(String.class) +" 2K"+ txtRoll.charAt(0)+txtRoll.charAt(1);
                txtHall = snapshot.child("Hall").getValue(String.class);


                // Log.d(TAG, "Value is: " + value);

                Name.setText(txtName);
                //Roll.setText(" 2K"+txtRoll);
                Department.setText(txtDepartment);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){

        }


        // initialize profile
        // with method findViewById()
        profile = findViewById(R.id.profile);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        profile.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Profile.class);
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
        about = findViewById(R.id.about);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        about.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this,About.class);

            startActivity(intent);
        });
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

            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Department.class);
            startActivity(intent);
        });

        // initialize hall
        // with method findViewById()
        hall = findViewById(R.id.hall);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        hall.setOnClickListener(v -> {
            if(Objects.equals(txtHall, "Bangabandhu Sheikh Mujibur Rahman Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, BBH.class);
                startActivity(intent);
            }

            else if(Objects.equals(txtHall, "Fazlul Haque Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, FazlulHaqueHall.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtHall, "Lalan Shah Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, LalanShah.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtHall, "Khan Jahan Ali Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, KJAH.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtHall, "Rokeya Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, RokeyaHall.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtHall, "Dr. M.A. Rashid Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, ARH.class);
                startActivity(intent);
            }
            else if(Objects.equals(txtHall, "Amar Ekushey Hall")){
                Intent intent = new Intent(HomeActivityStudent.this, AEH.class);
                startActivity(intent);
            }
            Toast.makeText(this, "Welcome to " + txtHall, Toast.LENGTH_SHORT).show();

        });

        // initialize library
        // with method findViewById()
        library = findViewById(R.id.library);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        library.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Library.class);
            startActivity(intent);
        });
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
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, AcademicUG.class);
            startActivity(intent);
        });

        // initialize bus
        // with method findViewById()
         bus = findViewById(R.id.bus);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        bus.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Bus.class);
            startActivity(intent);
        });

        // initialize website
        // with method findViewById()
        website = findViewById(R.id.website);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        website.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Website.class);
            startActivity(intent);
        });

        // initialize teacher
        // with method findViewById()
        teacher = findViewById(R.id.teacher);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        teacher.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, Teacher.class);
            startActivity(intent);
        });

        myprofile = findViewById(R.id.my_profile);

        // Apply OnClickListener  to imageView to
        // switch from one activity to another
        myprofile.setOnClickListener(v -> {
            // Intent class will help to go to next activity using
            // it's object named intent.
            // SecondActivity is the name of new created EmptyActivity.
            Intent intent = new Intent(HomeActivityStudent.this, MyProfile.class);
            startActivity(intent);
        });
    }

    private void signout() {

            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(HomeActivityStudent.this, LoginStudent.class);
            intent.addFlags((Intent.FLAG_ACTIVITY_NEW_TASK) | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
    }

}