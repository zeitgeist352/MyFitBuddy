package com.Settings;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.okButton.setOnClickListener(v -> {
            finish();
        });
    }





}
