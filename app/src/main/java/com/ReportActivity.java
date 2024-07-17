package com;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.exercises.ExerciseAdapter;
import com.exercises.ExerciseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.R;
import com.myfitbuddy.databinding.ActivityMainBinding;
import com.myfitbuddy.databinding.ActivityReportBinding;
import com.nutrition.Nutrient;
import com.nutrition.NutrientList;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int thisWeekPoints = 0;
    private int lastWeekPoints = 0;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private ArrayList<ExerciseModel> exerciseList;
    private NutrientList nutrientList;
    private final int kcalConst = 7700;

    private ActivityReportBinding binding;

    private ExerciseAdapter exerciseAdapter;
    TextView caloriesText;
    TextView consumedCaloriesText;
    TextView resultText;  

    private String reportType = "weekly";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarReport.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        caloriesText = binding.caloriesText;
        consumedCaloriesText = binding.consumedCaloriesText;
        resultText = binding.resultText;  

        nutrientList = new NutrientList(new ArrayList<>());
        loadNutrientsFromDb();

        Button buttonWeeklyReport = findViewById(R.id.buttonWeeklyReport);
        Button buttonMonthlyReport = findViewById(R.id.buttonMonthlyReport);

        buttonWeeklyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = "weekly";
                setTexts(currentUser.getUid(), reportType);
            }
        });

        buttonMonthlyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportType = "monthly";
                setTexts(currentUser.getUid(), reportType);
            }
        });

        setTexts(currentUser.getUid(), reportType);
    }

    private void loadNutrientsFromDb() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nutrients").child(userId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nutrientList.getNutrients().clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Nutrient nutrient = snapshot.getValue(Nutrient.class);
                        nutrientList.addNutrient(nutrient);
                    }
                    updateConsumedCalories();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to load nutrients", databaseError.toException());
                }
            });
        }
    }

    private void updateConsumedCalories() {
        consumedCaloriesText.setText("Calories consumed so far: " + nutrientList.getTotalCalories() + " kcal");
        calculateCaloricBalance();
    }

    private void setTexts(String userId, String reportType) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Number pointsNumber = documentSnapshot.getLong("points");
                if (pointsNumber != null) {
                    int points = pointsNumber.intValue();
                    caloriesText.setText("Your " + reportType + " kcal burn is: " + points * 3);
                } else {
                    caloriesText.setText("Your " + reportType + " kcal burn is: 0");
                }
                consumedCaloriesText.setText("Your " + reportType + " kcal intake is: " + nutrientList.getTotalCalories() + " kcal");
                calculateCaloricBalance();
            } else {
                Log.d("Error", "No such document with the current user id: " + userId);
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error fetching document", e));
    }

    private void calculateCaloricBalance() {
        double totalConsumed = nutrientList.getTotalCalories();
        double totalBurned = thisWeekPoints * 3;

        if (totalConsumed > totalBurned) {
            double excessCalories = totalConsumed - totalBurned;
            double weightGain =  excessCalories / kcalConst;
            resultText.setText(String.format("You are likely to gain %.2f kilos", weightGain));
        } else if (totalConsumed < totalBurned){
            double deficitCalories = totalBurned - totalConsumed;
            double weightLoss = deficitCalories / kcalConst;
            resultText.setText(String.format("You are likely to lose %.2f kilos", weightLoss));
        }
        else{
            resultText.setText(String.format("You are in balance, no kilos expected to be gained or burnt"));

        }
    }
}