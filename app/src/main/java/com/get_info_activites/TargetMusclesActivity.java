package com.get_info_activites;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.R;
import com.myfitbuddy.databinding.ActivityTargetMusclesBinding;

public class TargetMusclesActivity extends AppCompatActivity {
    private ActivityTargetMusclesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTargetMusclesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent comingIntent = getIntent();
        UserInfoHolder userInfoHolder = (UserInfoHolder) comingIntent.getSerializableExtra("userInfoHolder");

        binding.checkBoxChest.setOnCheckedChangeListener((buttonView, isChecked) -> updateBodyImage());
        binding.checkBoxBack.setOnCheckedChangeListener((buttonView, isChecked) -> updateBodyImage());
        binding.checkBoxArms.setOnCheckedChangeListener((buttonView, isChecked) -> updateBodyImage());
        binding.checkBoxLegs.setOnCheckedChangeListener((buttonView, isChecked) -> updateBodyImage());

        binding.buttonNext.setOnClickListener(view -> {
            userInfoHolder.setChest(binding.checkBoxChest.isChecked());
            userInfoHolder.setBack(binding.checkBoxBack.isChecked());
            userInfoHolder.setLeg(binding.checkBoxLegs.isChecked());
            userInfoHolder.setArm(binding.checkBoxArms.isChecked());

            Intent intent = new Intent(TargetMusclesActivity.this, BodyTypeActivity.class);
            intent.putExtra("userInfoHolder", userInfoHolder);
            startActivity(intent);
            finish();
        });

        binding.buttonPrev.setOnClickListener(view -> {
            Intent intentPrev = new Intent(TargetMusclesActivity.this, GoalActivity.class);
            startActivity(intentPrev);
            finish();
        });
    }

    private void updateBodyImage() {
        if (binding.checkBoxChest.isChecked()) {
            binding.imageView11.setImageResource(R.drawable.image_chest);
        }else if (binding.checkBoxBack.isChecked()) {
            binding.imageView11.setImageResource(R.drawable.image__back);
        }else if (binding.checkBoxArms.isChecked()) {
            binding.imageView11.setImageResource(R.drawable.image__arm);
        }else if (binding.checkBoxLegs.isChecked()) {
            binding.imageView11.setImageResource(R.drawable.image__leg);
        } else {
            binding.imageView11.setImageResource(R.drawable.anatomy);
        }
    }
}