package com;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.databinding.ActivityReportBinding;
import com.nutrition.Nutrient;
import com.nutrition.NutrientList;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    //defining variables

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int thisWeekPoints = 0;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private NutrientList nutrientList;
    private final int kcalConst = 7700;

    private ActivityReportBinding binding;

    private TextView caloriesText;
    private TextView consumedCaloriesText;
    private TextView resultText;
    private BarChart barChart;
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
        barChart = binding.barChart;

        barChart.setVisibility(View.INVISIBLE);

        binding.buttonWeeklyReport.setOnClickListener(v -> {
            barChart.setVisibility(View.VISIBLE);
            reportType = "weekly";
            setTexts(currentUser.getUid(), reportType);
            updateBarChart();
        });

        binding.buttonClear.setOnClickListener(v -> {
            clearBarChart();
        });

        nutrientList = new NutrientList(new ArrayList<>());
        loadNutrientsFromDb();

        setTexts(currentUser.getUid(), reportType);
        updateBarChart();
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
                    calculateBurn(currentUser.getUid());
                    updateBarChart();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to load nutrients", databaseError.toException());
                }
            });
        }
    }
    
    private void calculateBurn(String userId) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Number number = documentSnapshot.getLong("points");
                if (number != null) {
                    thisWeekPoints = number.intValue() * 3;
                    calcBalance(thisWeekPoints);
                } else {
                    thisWeekPoints = 0;
                    calcBalance(thisWeekPoints);
                }
            } else {
                thisWeekPoints = 0;
                calcBalance(thisWeekPoints);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
            thisWeekPoints = 0;
            calcBalance(thisWeekPoints);
        });
    }

    //it gives a burns kcal message
    private void setTexts(String userId, String reportType) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Number pointsNumber = documentSnapshot.getLong("points");
                if (pointsNumber != null) {
                    int points = pointsNumber.intValue();
                    caloriesText.setText("Your " + reportType + " kcal burn is: " + points * 3 + " kcal");
                } else {
                    caloriesText.setText("Your " + reportType + " kcal burn is: 0");
                }
                consumedCaloriesText.setText("Your " + reportType + " kcal intake is: " + nutrientList.getTotalCalories() + " kcal");
            } else {
                Log.d("Error", "No such document with the current user id: " + userId);
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error fetching document", e));
    }

    //it gives a message for a user such as you are in balance or you are likely to gain kilos
    private void calcBalance(int thisWeekPoints) {
        double totalConsumed = nutrientList.getTotalCalories();
        double totalBurned = thisWeekPoints;

        if (totalConsumed > totalBurned) {
            double excessCalories = totalConsumed - totalBurned;
            double weightGain = excessCalories / kcalConst;
            resultText.setText(String.format("You are likely to gain %.2f kilos", weightGain));
        } else if (totalConsumed < totalBurned) {
            double deficitCalories = totalBurned - totalConsumed;
            double weightLoss = deficitCalories / kcalConst;
            resultText.setText(String.format("You are likely to lose %.2f kilos", weightLoss));
        } else {
            resultText.setText("You are in balance, no kilos expected to be gained or lost");
        }
    }

    private void updateBarChart() {
        List<BarEntry> intakeEntries = new ArrayList<>();
        List<BarEntry> burnEntries = new ArrayList<>();

        for (int day = 0; day < 7; day++) {
            intakeEntries.add(new BarEntry(day, (float) nutrientList.getTotalCalories()));
            burnEntries.add(new BarEntry(day, (float) thisWeekPoints));
        }

        BarDataSet intakeDataSet = new BarDataSet(intakeEntries, "Calorie Intake");
        intakeDataSet.setColor(Color.GREEN);

        BarDataSet burnDataSet = new BarDataSet(burnEntries, "Calorie Burn");
        burnDataSet.setColor(Color.RED);

        BarData barData = new BarData(intakeDataSet, burnDataSet);
        barData.setBarWidth(0.3f);

        barChart.setData(barData);
        barChart.groupBars(0f, 0.4f, 0.02f);

        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "Day " + (int) value;
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);

        barChart.getDescription().setEnabled(false);
    }

    private void clearBarChart()
    {
        BarData barData = barChart.getData();
        for(IBarDataSet dataSet : barData.getDataSets())
        {
            for(int i = 0; i < dataSet.getEntryCount(); i++)
            {
                BarEntry entry = dataSet.getEntryForIndex(i);
                entry.setY(0);
            }
        }
        barChart.invalidate();
        Toast.makeText(this, "Bar chart has been cleared", Toast.LENGTH_SHORT).show();
    }
}