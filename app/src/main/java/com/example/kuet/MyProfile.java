package com.example.kuet;

import static android.content.ContentValues.TAG;
import static com.google.firebase.database.FirebaseDatabase.getInstance;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.kuet.HomeActivity.HomeActivityStudent;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyProfile extends AppCompatActivity {

    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //storage
    DatabaseReference storageReference;
    //path where images of user profile and cover will be stored
    String storagePath = "Users_Profile_Cover_Imgs/";
    ProgressDialog pd;
    private DatePickerDialog picker;

    //permissions constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    //image pick constants
    private static final int IMAGE_PICK_CAMERA_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    //permissions array
    String[] cameraPermissions;
    String[] storagePermissions;
    //image picked will be samed in this uri
    Uri image_rui = null;
    FloatingActionButton fab;
    String profileOrCoverPhoto;
    Uri image_uri;
    String uid,userID;

    TextView Mobile,Name,Roll,Home,College,DOB,BG,departmentTV,Hall,Email;
    ImageView Image;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final String txtdoB = DOB.getText().toString();

        Map<String, Object> Result = new HashMap<>();
        Result.put("Birthday", txtdoB);

        databaseReference.child(userID).updateChildren(Result);
        Intent intent = new Intent(MyProfile.this, HomeActivityStudent.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");



        /*MaterialButton Save = findViewById(R.id.save);*/
        Name = findViewById(R.id.idTVName);
        Roll = findViewById(R.id.Roll);
        Home = findViewById(R.id.home);
        College = findViewById(R.id.college);
        DOB = findViewById(R.id.dateofbirth);
        BG = findViewById(R.id.blood_group);
        Mobile = findViewById(R.id.idTVPhone);
        departmentTV = findViewById(R.id.dept);
        Hall = findViewById(R.id.hall);
        Email = findViewById(R.id.email);
        Image = findViewById(R.id.idIVContact);
        fab = findViewById(R.id.fab);

        /*Spinner spinner = findViewById(R.id.dept_spinner);
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
*/
        /*Email = findViewById(R.id.email);*/
        //final MaterialButton Register = findViewById(R.id.register);


        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference(); //firebase storage reference
        assert user != null;
        userID = user.getUid();
        pd = new ProgressDialog(MyProfile.this);



        /*String Dept=spinner.getSelectedItem().toString();
        String Hll = spinner2.getSelectedItem().toString();

        //Toast.makeText(MyProfile.this,""+Dept,Toast.LENGTH_SHORT).show();

        Map<String, Object> department = new HashMap<>();
        department.put("Department", Dept);

        spinner.setSelection(adapter.getPosition(Dept));

        myRef.child(userID).updateChildren(department);

        Map<String, Object> hall = new HashMap<>();
        hall.put("Hall", Hll);

        myRef.child(userID).updateChildren(hall);

        spinner2.setSelection(adapter2.getPosition(Dept));
*/

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String txtName = snapshot.child("Name").getValue(String.class);
                String txtRoll = snapshot.child("Roll").getValue(String.class);
                String txtBG = snapshot.child("Blood_Group").getValue(String.class);
                String txtDepartment = snapshot.child("Department").getValue(String.class);
                String txtCollege = snapshot.child("College").getValue(String.class);
                String txtHomeTown = snapshot.child("Home").getValue(String.class);
                String txtHall = snapshot.child("Hall").getValue(String.class);
                String txtMobile = snapshot.child("Mobile").getValue(String.class);
                String txtdob = snapshot.child("Birthday").getValue(String.class);
                String txtEmail = snapshot.child("Email").getValue(String.class);
                assert txtRoll != null;
                String Batch = txtDepartment + " 2K" + txtRoll.charAt(0) + txtRoll.charAt(1);
                // Log.d(TAG, "Value is: " + value);

               /* int spinnerPosition = adapter.getPosition(txtDepartment);
                int spinnerPosition2 = adapter2.getPosition(txtHall);*/

                Name.setText(txtName);
                //Toast.makeText(this, Name.getText(), Toast.LENGTH_SHORT).show();
                Roll.setText(txtRoll);
              /*  spinner.setSelection(spinnerPosition);
                spinner2.setSelection(spinnerPosition2);*/
                Home.setText(txtHomeTown);
                Hall.setText(txtHall);
                Mobile.setText(txtMobile);
                DOB.setText(txtdob);
                College.setText(txtCollege);
                BG.setText(txtBG);
                departmentTV.setText(Batch);
                Email.setText(txtEmail);
                //Email.setText(txtEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        fab.setOnClickListener(v -> showEditProfileDialog());



    }

    private boolean checkStoragePermission() {
        //check if storage permission is enabled or not
        //return true if enabled
        //return false if not enabled
        return ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
    }

    private void requestStoragePermission() {
        //request runtime storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(storagePermissions, STORAGE_REQUEST_CODE);
        }
    }


    private boolean checkCameraPermission() {
        //check if storage permission is enabled or not
        //return true if enabled
        //return false if not enabled
        boolean result = ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(MyProfile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        //request runtime storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE);
        }
    }


    private void showEditProfileDialog() {
        /*Show dialog containing options
         * 1) Edit Profile Picture
         * 2) Edit Cover Photo
         * 3) Edit Name
         * 4) Edit Phone*/

        //options to show in dialog
        String[] options = {"Edit Name","Edit Roll","Edit Mobile","Edit Home","Edit College","Edit Date of Birth","Edit Blood Group"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        //set title
        builder.setTitle("Choose Action");
        //set items to dialog
        builder.setItems(options, (dialog, which) -> {
            //handle dialog item clicks
/*            if (which == 0) {
                //Edit Profile clicked
                pd.setMessage("Updating Profile Picture");
                profileOrCoverPhoto = "image"; //i.e. changing profile picture, make sure to assign same value
                showImagePicDialog();
            } else */if (which == 0) {
                //Edit Name clicked
                showNameDialog();
                //calling method and pass key "name" as parameter to update it's value in database

            }else if (which == 1) {
                //Edit Name clicked
                showRollDialog();
                //calling method and pass key "name" as parameter to update it's value in database

            } else if (which == 2) {
                showMobileUpdateDialog();
            }else if (which == 3) {
                showHomeUpdateDialog();
            }else if (which == 4) {
                showCollegeUpdateDialog();
            }else if (which == 5) {
                showDateofBirthUpdateDialog();
            }else if (which == 6) {
                showBloodGroupUpdateDialog();
            }
        });

        builder.create().show();
    }

    private void showBloodGroupUpdateDialog() {
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
                result.put("Blood_Group", value);
                BG.setText(value);

                databaseReference.child(user.getUid()).updateChildren(result)
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
    }

    private void showDateofBirthUpdateDialog() {

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        picker = new DatePickerDialog(MyProfile.this, (view, year1, month1, dayOfMonth) -> DOB.setText((dayOfMonth + "/" + (month1 + 1) + "/" + year1)), year, month, day);


        //Toast.makeText(MyProfile.this,""+DOB,Toast.LENGTH_SHORT).show();

        picker.show();

        //Toast.makeText(MyProfile.this, "Please select date of birth", Toast.LENGTH_SHORT).show();


        final String txtdoB = DOB.getText().toString();

        Map<String, Object> Result = new HashMap<>();
        Result.put("Birthday", txtdoB);

        databaseReference.child(userID).updateChildren(Result);
    }

    private void showCollegeUpdateDialog() {
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
                result.put("College", value);
                College.setText(value);
                databaseReference.child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(unused -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                        })
                        .addOnFailureListener(e -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(MyProfile.this, "Please enter your college name.", Toast.LENGTH_SHORT).show();
            }

        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void showHomeUpdateDialog() {
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
                result.put("Home", value);
                Home.setText(value);
                databaseReference.child(user.getUid()).updateChildren(result)
                        .addOnSuccessListener(unused -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this, "Updated...", Toast.LENGTH_SHORT).show();


                        })
                        .addOnFailureListener(e -> {
                            pd.dismiss();
                            Toast.makeText(MyProfile.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(MyProfile.this, "Please enter your home.", Toast.LENGTH_SHORT).show();
            }

        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    private void showRollDialog() {
        pd.setMessage("Updating Roll");
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Update Roll");

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
                databaseReference.child(user.getUid()).updateChildren(result)
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
    }

    private void showNameDialog() {

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
                databaseReference.child(user.getUid()).updateChildren(result)
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
    }

    private void showMobileUpdateDialog() {
        pd.setMessage("Updating Mobile");
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Update Mobile");

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

                databaseReference.child(user.getUid()).updateChildren(result)
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
    }
    private void showImagePicDialog() {
        //show dialog containing options Camera and Gallery to pick the image

        String[] options = {"Camera", "Gallery"};
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        //set title
        builder.setTitle("Pick Image From");
        //set items to dialog
        builder.setItems(options, (dialog, which) -> {
            //handle dialog item clicks
            if (which == 0) {
                //Camera clicked
                if (!checkCameraPermission()) {
                    requestCameraPermission();
                } else {
                    pickFromCamera();
                }
            } else if (which == 1) {
                //Gallery clicked
                if (!checkStoragePermission()) {
                    requestStoragePermission();
                } else {
                    pickFromGallery();
                }
            }
        });
        //create and show dialog
        builder.create().show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*This method called when user press Allow or Deny from permission request dialog
         * here we will handle permission cases (allowed & denied)*/

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                //picking from camera, first check if camera and storage permissions allowed or not
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        //permissions enabled
                        pickFromCamera();
                    } else {
                        //pemissions denied
                        Toast.makeText(MyProfile.this, "Please enable camera & storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {

                //picking from galler, first check if storage permissions allowed or not
                if (grantResults.length > 0) {
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccepted) {
                        //permissions enabled
                        pickFromGallery();
                    } else {
                        //pemissions denied
                        Toast.makeText(MyProfile.this, "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*This method will be called after picking image from Camera or Gallery*/
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                //image is picked from gallery, get uri of image
                image_uri = data.getData();

                uploadProfileCoverPhoto(image_uri);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                //image is picked from camera, get uri of image

                uploadProfileCoverPhoto(image_uri);

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfileCoverPhoto(final Uri uri) {
        //show progress
        pd.show();

        /*Instead of creating separate function for Profile Picture and Cover Photo
         * i'm doing work for both in same function
         *
         * To add check ill add a string variable and assign it value "image" when user clicks
         * "Edit Profile Pic", and assign it value "cover" when user clicks "Edit Cover Photo"
         * Here: image is the key in each user containing url of user's profile picture
         *       cover is the key in each user containing url of user's cover photo */

        /*The parameter "image_uri" contains the uri of image picked either from camera or gallery
         * We will use UID of the currently signed in user as name of the image so there will be only one image for
         * profile and one image for cover for each user*/

        //path and name of image to be stored in firebase storage
        //e.g. Users_Profile_Cover_Imgs/image_e12f3456f789.jpg
        //e.g. Users_Profile_Cover_Imgs/cover_c123n4567g89.jpg
        String filePathAndName = storagePath + "" + profileOrCoverPhoto + "_" + user.getUid();

        DatabaseReference storageReference2nd = storageReference.child(filePathAndName);
      /* storageReference2nd.putFile(uri)
                .addOnSuccessListener((OnSuccessListener<UploadTask.TaskSnapshot>) taskSnapshot -> {
                    //image is uploaded to storage, now get it's url and store in user's database
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    final Uri downloadUri = uriTask.getResult();

                    //check if image is uploaded or not and url is received
                    if (uriTask.isSuccessful()) {
                        //image uploaded
                        //add/update url in user's database
                        HashMap<String, Object> results = new HashMap<>();
                        *//*First Parameter is profileOrCoverPhoto that has value "image" or "cover"
                          which are keys in user's database where url of image will be saved in one
                          of them
                          Second Parameter contains the url of the image stored in firebase storage, this
                          url will be saved as value against key "image" or "cover"*//*
                        results.put(profileOrCoverPhoto, downloadUri.toString());

                        databaseReference.child(user.getUid()).updateChildren(results)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //url in database of user is added successfully
                                        //dismiss progress bar
                                        pd.dismiss();
                                        Toast.makeText(MyProfile.this, "Image Updated...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //error adding url in database of user
                                        //dismiss progress bar
                                        pd.dismiss();
                                        Toast.makeText(MyProfile.this, "Error Updating Image...", Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //if user edit his name, also change it from hist posts
                        if (profileOrCoverPhoto.equals("image")) {
                            DatabaseReference ref = getInstance().getReference("Posts");
                            Query query = ref.orderByChild("uid").equalTo(uid);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String child = ds.getKey();
                                        assert child != null;
                                        dataSnapshot.getRef().child(child).child("uDp").setValue(downloadUri.toString());
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //update user image in current users comments on posts
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String child = ds.getKey();
                                        assert child != null;
                                        if (dataSnapshot.child(child).hasChild("Comments")) {
                                            String child1 = "" + dataSnapshot.child(child).getKey();
                                            Query child2 = getInstance().getReference("Posts").child(child1).child("Comments").orderByChild("uid").equalTo(uid);
                                            child2.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                        String child = ds.getKey();
                                                        dataSnapshot.getRef().child(child).child("uDp").setValue(downloadUri.toString());
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                    } else {
                        //error
                        pd.dismiss();
                        Toast.makeText(MyProfile.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //there were some error(s), get and show error message, dismiss progress dialog
                        pd.dismiss();
                        Toast.makeText(MyProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/


    }

    private void pickFromCamera() {
        //Intent of picking image from device camera
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //put image uri
        image_uri = MyProfile.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intent to start camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {
        //pick from gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }
}