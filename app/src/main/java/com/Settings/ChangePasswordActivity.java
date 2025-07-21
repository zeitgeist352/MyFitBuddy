package com.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.myfitbuddy.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.toolbarReport.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        binding.okButton.setOnClickListener(v -> {
            String currentPassword = binding.editTextCurrentPassword.getText().toString();
            String newPassword = binding.editTextNewPassword.getText().toString();
            String confirmNewPassword = binding.editTextConfirmNewPassword.getText().toString();

            if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmNewPassword)) {
                Toast.makeText(ChangePasswordActivity.this, "All fields are needed to be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(ChangePasswordActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(currentPassword, newPassword);
        });
    }

    private void changePassword(String currentPassword, String newPassword) {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Error in updating password", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}