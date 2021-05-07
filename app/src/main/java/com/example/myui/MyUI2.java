package com.example.myui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;


import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MyUI2 extends AppCompatActivity {

    //linking Activity Pages

    public TextView textView;

    //login_page
    EditText txtEmail,txtPassword;
    Button btn_login;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_u_i2);

        //linking Activity Pages
        textView =  findViewById(R.id.textView4);
        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MyUI2.this,registeration.class);
                startActivity(intent1);
            }
        });

        txtEmail =findViewById(R.id.editTextEmail);
        txtPassword =findViewById(R.id.editTextTextPassword);
        btn_login=findViewById(R.id.btn_login);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

//email,password,confirm password slots should not be vacant
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(MyUI2.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(MyUI2.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }




                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(MyUI2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(MyUI2.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),user_profile.class));
                                }
                                else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MyUI2.this, "Login Failed or User Not Available", Toast.LENGTH_SHORT).show();
                                }


                                }
                            });
                }




        });
    }
}