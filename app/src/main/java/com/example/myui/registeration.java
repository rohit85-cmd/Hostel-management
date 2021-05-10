package com.example.myui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class registeration extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText txtEmail, txtPassword, txtConfirmPassword, txtFullName, txtCityName, txtContactNo, txtParentContactNo, txtCurrentStudyingYear;
    Button btn_register;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);



        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);
        txtConfirmPassword = findViewById(R.id.txt_confirm_password);
        txtFullName = findViewById(R.id.text_fullname);
        txtCityName = findViewById(R.id.text_city);
        txtContactNo = findViewById(R.id.text_contact_no);
        txtParentContactNo = findViewById(R.id.text_parent_contact_no);
        txtCurrentStudyingYear = findViewById(R.id.text_studying_yr);

        btn_register = findViewById(R.id.button_register);

        progressBar = findViewById(R.id.progressbar);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();




        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();
                String fullName = txtFullName.getText().toString();
                String cityName = txtCityName.getText().toString();
                String contactNumber = txtContactNo.getText().toString();
                String parentContactNumber = txtParentContactNo.getText().toString();
                String currentStudyingYear = txtCurrentStudyingYear.getText().toString();

//email,password,confirm password slots should not be vacant
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(registeration.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(registeration.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(registeration.this,"Please Enter Confirm Password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(registeration.this,"Please Enter Full Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cityName)){
                    Toast.makeText(registeration.this,"Please Enter City Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(contactNumber)){
                    Toast.makeText(registeration.this,"Please Enter Contact Number",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(parentContactNumber)){
                    Toast.makeText(registeration.this,"Please Enter Parent Contact Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(currentStudyingYear)){
                    Toast.makeText(registeration.this,"Please Enter Current Studying Year",Toast.LENGTH_SHORT).show();
                    return;
                }


//password length >6
                if(password.length()<6){
                    Toast.makeText(registeration.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(confirmPassword.length()<6){
                    Toast.makeText(registeration.this, "Password too short", Toast.LENGTH_SHORT).show();
                    return;
                }



// contact number =10 characters
                if((contactNumber.length() > 10) || (contactNumber.length() < 10)){
                    Toast.makeText(registeration.this, "Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((parentContactNumber.length() > 10) || (parentContactNumber.length() < 10)){
                    Toast.makeText(registeration.this, "Enter Valid Contact Number", Toast.LENGTH_SHORT).show();
                    return;
                }





                progressBar.setVisibility(View.VISIBLE);

// cross verifying the password and confirm password are matching or not
                if(password.equals(confirmPassword)){

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(registeration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);


                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(registeration.this, "User Created", Toast.LENGTH_SHORT).show();



                                        //In fireStore first collection is there, after collection there is document


                                        userID = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                                        DocumentReference documentReference = fstore.collection("users").document(userID);
                                        Map<String,Object> user = new HashMap<>();
                                        user.put("email",email);
                                        user.put("password",password);
                                        user.put("fName",fullName);
                                        user.put("cName",cityName);
                                        user.put("cNumber",contactNumber);
                                        user.put("pContactNumber",parentContactNumber);
                                        user.put("CSYear",currentStudyingYear);

                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "onSuccess: User is created for " + userID);
                                            }
                                        });

                                        documentReference.set(user).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "onFailure: Error",e );
                                            }
                                        });






                                        startActivity(new Intent(getApplicationContext(),MyUI2.class));


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(registeration.this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });
                }

            }

        });




    }








    }
