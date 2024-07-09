package com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.R;

public class NotificiationActivity extends AppCompatActivity {

    private com.myfitbuddy.databinding.ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificiation);

        Toolbar toolbar = findViewById(R.id.toolbarNotification);
        setSupportActionBar(toolbar);

        // Add an onClickListener to the navigation icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificiationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}