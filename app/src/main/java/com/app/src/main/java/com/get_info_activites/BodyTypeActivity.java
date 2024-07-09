package com.get_info_activites;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.myfitbuddy.R;

import com.myfitbuddy.databinding.ActivityBodyTypeBinding;

public class BodyTypeActivity extends AppCompatActivity {
    private ActivityBodyTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBodyTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent comingIntent = getIntent();
        UserInfoHolder userInfoHolder = (UserInfoHolder) comingIntent.getSerializableExtra("userInfoHolder");


        binding.buttonNextBodyType.setOnClickListener(view -> {

            RadioGroup radioGroup = binding.radioGroupType;
            int selectedId = radioGroup.getCheckedRadioButtonId();

            if(selectedId == -1){
                throw new IllegalStateException("Choose a body type");
            }else {
                if (selectedId == R.id.radioButton_muscular) {
                    userInfoHolder.setBodyType("muscular");
                } else if (selectedId == R.id.radioButton_fat) {
                    userInfoHolder.setBodyType("fat");
                } else if (selectedId == R.id.radioButton_thin) {
                    userInfoHolder.setBodyType("thin");
                } else if (selectedId == R.id.radioButton_normal) {
                    userInfoHolder.setBodyType("normal");
                } else {
                    throw new IllegalStateException("Unexpected value: " + selectedId);
                }

                Intent intent = new Intent(BodyTypeActivity.this, GetPushupActivity.class);
                intent.putExtra("userInfoHolder", userInfoHolder);
                startActivity(intent);
                finish();
            }
        });
        binding.buttonPrevBodyType.setOnClickListener(view -> {
            Intent intentPrev;
            if (userInfoHolder.getPurpose().equals("loseWeight"))
                intentPrev = new Intent(BodyTypeActivity.this, GoalActivity.class);
            else
                intentPrev = new Intent(BodyTypeActivity.this, TargetMusclesActivity.class);
            startActivity(intentPrev);
            finish();
        });



    }
}