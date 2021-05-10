package com.example.myui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class user_profile extends AppCompatActivity {
    TextView email,fullname,cityname,contact_no,parent_contact_no,
            current_studying_year;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        email = findViewById(R.id.Profile_email_textView);
        fullname = findViewById(R.id.Profile_fullname_textView);
        cityname = findViewById(R.id.Profile_city_textView);
        contact_no = findViewById(R.id.Profile_Contact_textView);
        parent_contact_no = findViewById(R.id.Profile_ParentContact_textView);
        current_studying_year = findViewById(R.id.Profile_CurrentStudyingYear_textView);

        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                email.setText(documentSnapshot.getString("email"));
                fullname.setText(documentSnapshot.getString("fName"));
                cityname.setText(documentSnapshot.getString("cName"));
                contact_no.setText(documentSnapshot.getString("cNumber"));
                parent_contact_no.setText(documentSnapshot.getString("pContactNumber"));
                current_studying_year.setText(documentSnapshot.getString("CSYear"));

            }
        });

    }
}