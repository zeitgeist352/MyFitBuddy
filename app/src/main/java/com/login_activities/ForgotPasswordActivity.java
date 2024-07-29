package com.login_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.myfitbuddy.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(activityForgotPasswordBinding.getRoot());

        activityForgotPasswordBinding.toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        activityForgotPasswordBinding.passResetButton.setOnClickListener(view -> sendResetMail());
    }
    //reset mail for the password
    public void sendResetMail() {
        String email = activityForgotPasswordBinding.editTextEmailForgotPass.getText().toString();

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        if (!email.isEmpty()) {
            db.collection("Users").whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    // Email exists
                                    auth.sendPasswordResetEmail(email);
                                    Toast.makeText(ForgotPasswordActivity.this, "Reset mail has been sent", Toast.LENGTH_LONG).show();
                                } else {
                                    // Email does not exist
                                    Toast.makeText(ForgotPasswordActivity.this, "Email does not exist", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // Error occurred
                                Toast.makeText(ForgotPasswordActivity.this, "Error checking email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Fill in the blanks", Toast.LENGTH_LONG).show();
        }
    }
}