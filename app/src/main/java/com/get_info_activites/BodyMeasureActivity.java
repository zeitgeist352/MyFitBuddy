package com.get_info_activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.databinding.ActivityBodyMeasureBinding;

public class BodyMeasureActivity extends AppCompatActivity {
    private ActivityBodyMeasureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBodyMeasureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent comingIntent = getIntent();
        UserInfoHolder userInfoHolder = (UserInfoHolder) comingIntent.getSerializableExtra("userInfoHolder");

        binding.buttonNextMeasure.setOnClickListener(view -> {

            if(binding.waist.getText().toString().isEmpty() || binding.arm.getText().toString().isEmpty() || binding.leg.getText().toString().isEmpty() || binding.hip.getText().toString().isEmpty()){
                Toast.makeText(this,"fill in the blanks",Toast.LENGTH_LONG).show();
                return;
            }
            userInfoHolder.setArmCircum(Integer.parseInt(binding.arm.getText().toString()));
            userInfoHolder.setHipCircum(Integer.parseInt(binding.hip.getText().toString()));
            userInfoHolder.setLegCircum(Integer.parseInt(binding.leg.getText().toString()));
            userInfoHolder.setWaistCircum(Integer.parseInt(binding.waist.getText().toString()));

            Intent intent = new Intent(BodyMeasureActivity.this, PreferredDaysActivity.class);
            intent.putExtra("userInfoHolder", userInfoHolder);

            startActivity(intent);
            finish();
        });
        binding.buttonPrevMeasure.setOnClickListener(view -> {
            Intent intentPrev = new Intent(BodyMeasureActivity.this, BodyInfoActivity.class);
            startActivity(intentPrev);
            finish();
        });


    }
}