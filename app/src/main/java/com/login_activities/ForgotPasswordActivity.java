package com.login_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.myfitbuddy.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private FirebaseAuth auth;

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

    public void sendResetMail() {
        String email = activityForgotPasswordBinding.editTextEmailForgotPass.getText().toString();

        auth = FirebaseAuth.getInstance();
        if (!email.isEmpty()){
            auth.sendPasswordResetEmail(email);
            Toast.makeText(this,"reset mail has been sent", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"fill in the blanks", Toast.LENGTH_LONG).show();
        }

    }
}