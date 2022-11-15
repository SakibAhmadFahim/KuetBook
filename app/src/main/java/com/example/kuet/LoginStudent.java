package com.example.kuet;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginStudent extends AppCompatActivity {


    GoogleSignInClient mGoogleSignInClient;
    SignInButton  mGoogleLoginbtn;
    EditText Email,Password;
    TextView rgstr,Recover;
    CheckBox rememberme;
    private FirebaseAuth mAuth;

    //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-9241c-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        rgstr=findViewById(R.id.register);
        Email = findViewById(R.id.tmail);
        Password = findViewById(R.id.tpass);
        mGoogleLoginbtn = findViewById(R.id.google);
        mGoogleLoginbtn.setSize(SignInButton.SIZE_STANDARD);
        rememberme= findViewById(R.id.rembrme);
        Recover= findViewById(R.id.recover);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        ImageView imageView=findViewById(R.id.imageview_logo);
        imageView.setImageResource(R.drawable.ic_hide_pwd);
        imageView.setOnClickListener(v -> {
            if(Password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.ic_show_pwd);
            }
            else{
                Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageView.setImageResource(R.drawable.ic_hide_pwd);
            }
        });



       // googlebtn.setOnClickListener(v -> signIn());

        MaterialButton signin = findViewById(R.id.signin);


        signin.setOnClickListener(v -> {
            final String EmailTxt = Email.getText().toString();
            final String PasswordTxt = Password.getText().toString();

            if(EmailTxt.isEmpty()){
                Toast.makeText(LoginStudent.this,"Please enter your email address",Toast.LENGTH_SHORT).show();
            }

            else if(PasswordTxt.isEmpty()){
                Toast.makeText(LoginStudent.this,"Please enter your password",Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(EmailTxt,PasswordTxt).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        assert firebaseUser != null;
                        if(firebaseUser.isEmailVerified()){


                                    Intent intent=new Intent(LoginStudent.this,HomeActivityStudent.class);

                                    Toast.makeText(LoginStudent.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                                        //startActivity(new Intent(LoginStudent.this, HomeActivityStudent.class));
                                    startActivity(intent);
                                    finish();
                        }
                        else {
                            firebaseUser.sendEmailVerification();
                            mAuth.signOut();
                            showAlertDialog();
                        }
                    }else{
                        Toast.makeText(LoginStudent.this, "Log in Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        rememberme.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()){
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","true");
                editor.apply();
                Toast.makeText(LoginStudent.this,"Checked",Toast.LENGTH_SHORT).show();
            }
            else if(compoundButton.isChecked()){
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember","false");
                editor.apply();
                Toast.makeText(LoginStudent.this,"UnChecked",Toast.LENGTH_SHORT).show();
            }
        });

        rgstr.setOnClickListener(v -> {
            Intent intent = new Intent(LoginStudent.this, RegistrationStudent.class);
            startActivity(intent);
            finish();
        });

        mGoogleLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultLauncher.launch(new Intent(mGoogleSignInClient.getSignInIntent()));
            }
        });





        rgstr.setPaintFlags(rgstr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Recover.setOnClickListener(v -> showRecoverPasswordDialog());

        Recover.setPaintFlags(Recover.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

       // mGoogleLoginbtn.setOnClickListener(v -> resultLauncher.launch(new Intent(mGoogleLoginbtn.getSignInIntent())));
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginStudent.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now.");

        builder.setPositiveButton("Continue", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout = new LinearLayout(this);
        EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", (dialog, which) -> {
            String email = emailEt.getText().toString().trim();
            beginRecovery(email);
        });


        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void beginRecovery(String email) {

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        Toast.makeText(LoginStudent.this,"Email Sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginStudent.this,"Failed...",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {

                    Toast.makeText(LoginStudent.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

                });
    }

    @Override
    protected void onStart() {
     super.onStart();
        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        /*if(){
            Intent intent=new Intent(LoginStudent.this,HomeActivityStudent.class);
            startActivity(intent);
        }
        else if(checkbox.equals("false")){
            Toast.makeText(this,"Please Sign In",Toast.LENGTH_SHORT).show();
        }*/
       FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null && checkbox.equals("true")){
           Toast.makeText(LoginStudent.this,"Already Logged In!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginStudent.this, HomeActivityStudent.class));
            finish();
      }
        else{
            Toast.makeText(LoginStudent.this,"Please Sign In",Toast.LENGTH_SHORT).show();
       }
  }

    ActivityResultLauncher<Intent>resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result){

            if(result.getResultCode() == Activity.RESULT_OK){
                Intent intent = result.getData();
                Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(intent);

                try{
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    assert account!= null;
                    firebaseAuthWithGoogle(account);

                } catch (ApiException e) {
                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
       // FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(LoginStudent.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseUser user=mAuth.getCurrentUser();
                        assert user != null;
                        Toast.makeText(LoginStudent.this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginStudent.this, HomeActivityStudent.class));
                        finish();
                    }
                }).addOnFailureListener(e -> Toast.makeText(LoginStudent.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());
    }
}
