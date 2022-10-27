package com.example.kuet;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginTeacher extends AppCompatActivity {


    GoogleSignInClient gsc;
    SignInButton googlebtn;
    private static final int RC_SIGN_IN=100;
    FirebaseAuth mAuth;

    //DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://kuet-9241c-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        final TextView rgstr=findViewById(R.id.register);
        final EditText Email = findViewById(R.id.tmail);
        final EditText Password = findViewById(R.id.tpass);
        googlebtn = findViewById(R.id.google);
        googlebtn.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);

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
        // MaterialButton register = (MaterialButton) findViewById(R.id.rgstrb);

        signin.setOnClickListener(v -> {
            final String EmailTxt = Email.getText().toString();
            final String PasswordTxt = Password.getText().toString();

            if(EmailTxt.isEmpty()){
                Toast.makeText(LoginTeacher.this,"Please enter your email address",Toast.LENGTH_SHORT).show();
            }

            else if(PasswordTxt.isEmpty()){
                Toast.makeText(LoginTeacher.this,"Please enter your password",Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(EmailTxt,PasswordTxt).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        assert firebaseUser != null;
                        if(firebaseUser.isEmailVerified()){
                            Toast.makeText(LoginTeacher.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginTeacher.this, HomeActivityTeacher.class));
                            finish();
                        }
                        else {
                            firebaseUser.sendEmailVerification();
                            mAuth.signOut();
                            showAlertDialog();
                        }
                    }else{
                        Toast.makeText(LoginTeacher.this, "Log in Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        rgstr.setOnClickListener(v -> {
            Intent intent = new Intent(LoginTeacher.this, RegistrationTeacher.class);
            startActivity(intent);
        });

        rgstr.setPaintFlags(rgstr.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        googlebtn.setOnClickListener(v -> resultLauncher.launch(new Intent(gsc.getSignInIntent())));
    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginTeacher.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please verify your email now. You can not login without email verification.");

        builder.setPositiveButton("Continue", (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_EMAIL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null ){
            Toast.makeText(LoginTeacher.this,"Already Logged In!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginTeacher.this, HomeActivityTeacher.class));
            finish();
        }
        else{
            Toast.makeText(LoginTeacher.this,"You can login now!",Toast.LENGTH_SHORT).show();
        }
    }


    ActivityResultLauncher<Intent>resultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode()== Activity.RESULT_OK){
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
    });

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account!= null;
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "signInWithCredential", task.getException());
                        Toast.makeText(LoginTeacher.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseUser user=mAuth.getCurrentUser();
                        assert user != null;
                        Toast.makeText(LoginTeacher.this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginTeacher.this, HomeActivityTeacher.class));
                        finish();
                    }
                }).addOnFailureListener(e -> Toast.makeText(LoginTeacher.this,""+e.getMessage(),Toast.LENGTH_SHORT).show());
    }


}
