package com.get_info_activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.databinding.ActivityBodyInfoBinding;

public class BodyInfoActivity extends AppCompatActivity {
    private ActivityBodyInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBodyInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent comingIntent = getIntent();
        UserInfoHolder userInfoHolder = (UserInfoHolder) comingIntent.getSerializableExtra("userInfoHolder");

        binding.buttonNextBodyInfo.setOnClickListener(view -> {

            if(binding.editTextNumberWeight.getText().toString().isEmpty() || binding.editTextNumberDecimalHeight.getText().toString().isEmpty() || binding.editTextNumberAge.getText().toString().isEmpty()){
                Toast.makeText(this,"fill in the blanks",Toast.LENGTH_LONG).show();
                return;
            }
            userInfoHolder.setWeight(Integer.parseInt(binding.editTextNumberWeight.getText().toString()));
            userInfoHolder.setHeight(Integer.parseInt(binding.editTextNumberDecimalHeight.getText().toString()));
            userInfoHolder.setAge(Integer.parseInt(binding.editTextNumberAge.getText().toString()));

            userInfoHolder.calculateIbm();

            Intent intent = new Intent(BodyInfoActivity.this, PreferredDaysActivity.class);
            intent.putExtra("userInfoHolder", userInfoHolder);

            startActivity(intent);
            finish();
        });
        binding.buttonPrevBodyInfo.setOnClickListener(view -> {
            Intent intentPrev = new Intent(BodyInfoActivity.this, GenderActivity.class);
            startActivity(intentPrev);
            finish();
        });


    }
}