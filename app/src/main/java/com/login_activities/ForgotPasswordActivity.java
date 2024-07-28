package com.login_activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.myfitbuddy.databinding.ActivityForgotPasswordBinding;
import com.get_info_activites.UserInfoManager;
import com.get_info_activites.UserInfoHolder;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding activityForgotPasswordBinding;
    private FirebaseAuth auth;
    private UserInfoManager userInfoManager;

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

        userInfoManager = UserInfoManager.getInstance(); 
    }

    // sending the reset password email 
    public void sendResetMail() {
        String email = activityForgotPasswordBinding.editTextEmailForgotPass.getText().toString();
        auth = FirebaseAuth.getInstance();

        if (!email.isEmpty()) {
            UserInfoHolder userInfo = userInfoManager.getUserInfoByEmail(email); 

            if (userInfo != null) {
                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Reset mail has been sent", Toast.LENGTH_LONG).show();
                    } 
                    else {
                        Toast.makeText(ForgotPasswordActivity.this, "Error occurred: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } 
            else {
                Toast.makeText(ForgotPasswordActivity.this, "User not found", Toast.LENGTH_LONG).show();
            }
        } 
        else {
            Toast.makeText(this, "Fill in the blanks", Toast.LENGTH_LONG).show();
        }
    }
}