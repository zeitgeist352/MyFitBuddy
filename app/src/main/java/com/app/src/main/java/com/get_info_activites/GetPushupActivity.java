package com.get_info_activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.R;
import com.myfitbuddy.databinding.ActivityGetPushupBinding;

public class GetPushupActivity extends AppCompatActivity {
    private ActivityGetPushupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetPushupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent comingIntent = getIntent();
        UserInfoHolder userInfoHolder = (UserInfoHolder) comingIntent.getSerializableExtra("userInfoHolder");

        binding.buttonNextPushup.setOnClickListener(view -> {

            RadioGroup radioGroup = binding.radioGroup3;
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                throw new IllegalStateException("Choose a pushup count");
            }
            else {
                if (selectedId == R.id.radioButton_1_5_reps) {
                    userInfoHolder.setPushupCount(5);
                } else if (selectedId == R.id.radioButton_5_10_reps) {
                    userInfoHolder.setPushupCount(10);
                } else if (selectedId == R.id.radioButton_10_20_reps) {
                    userInfoHolder.setPushupCount(20);
                } else if (selectedId == R.id.radioButton_20_more_reps) {
                    userInfoHolder.setPushupCount(25);
                } else {
                    throw new IllegalStateException("Unexpected value: " + selectedId);
                }

                Intent intent = new Intent(GetPushupActivity.this, LoadingInfoSessionSActivity.class);
                intent.putExtra("userInfoHolder", userInfoHolder);
                startActivity(intent);
                finish();
            }
        });
        binding.buttonPrevPushup.setOnClickListener(view -> {
            Intent intentPrev = new Intent(GetPushupActivity.this, BodyTypeActivity.class);
            startActivity(intentPrev);
            finish();
        });

    }
}