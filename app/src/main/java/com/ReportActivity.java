package com;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.myfitbuddy.R;
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
    private int countDay = 0;
    private Number pointsA;
    private double mondayHolder;
    private double tuesdayHolder;
    private double wednesdayHolder;
    private double thursdayHolder;
    private double fridayHolder;
    private double saturdayHolder;
    private double sundayHolder;

    private ActivityReportBinding binding;

    private TextView caloriesText;
    private TextView consumedCaloriesText;
    private TextView resultText;
    private BarChart barChart;
    private String reportType = "weekly";
    private EditText editTextDay;
    private Button selectDay;

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

        barChart.setVisibility(View.VISIBLE);

        loadDailyData();
        calculateWeeklyConsumption();

        binding.buttonClear.setOnClickListener(v -> {
            clearBarChart();
        });

        Button selectDayButton = findViewById(R.id.selectDayButton);
        selectDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectDayDialog();
            }
        });

        binding.buttonAdd.setOnClickListener(v ->{
            String day = String.valueOf(selectDayButton.getText());
            double calories = nutrientList.getTotalCalories();
            saveDataToFirebase(day,calories);
            BarData barData = barChart.getData();
            BarDataSet dataSet = (BarDataSet)barData.getDataSetByIndex(0);
            BarDataSet dataSetBurn = (BarDataSet)barData.getDataSetByIndex(1);

            switch (day) {
                case "Monday":
                    mondayHolder = calories;
                    dataSet.getEntryForIndex(0).setY((float) calories);
                    break;
                case "Tuesday":
                    tuesdayHolder = calories;
                    dataSet.getEntryForIndex(1).setY((float) calories);
                    break;
                case "Wednesday":
                    wednesdayHolder = calories;
                    dataSet.getEntryForIndex(2).setY((float) calories);
                    break;
                case "Thursday":
                    thursdayHolder = calories;
                    dataSet.getEntryForIndex(3).setY((float) calories);
                    break;
                case "Friday":
                    fridayHolder = calories;
                    dataSet.getEntryForIndex(4).setY((float) calories);
                    break;
                case "Saturday":
                    saturdayHolder = calories;
                    dataSet.getEntryForIndex(5).setY((float) calories);
                    break;
                case "Sunday":
                    sundayHolder = calories;
                    dataSet.getEntryForIndex(6).setY((float) calories);
                    break;
            }

            barChart.invalidate();
        });

        loadDailyData();
        nutrientList = new NutrientList(new ArrayList<>());
        loadNutrientsFromDb();
        setTexts(currentUser.getUid(), reportType);
        updateBarChart(currentUser.getUid());
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
                    updateBarChart(currentUser.getUid());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to load nutrients", databaseError.toException());
                }
            });
        }
    }

    private void saveDataToFirebase(String day, double calories) {
        String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dailyData").child(userId).child(day);

        databaseReference.setValue(calories).addOnSuccessListener(aVoid -> {
            Toast.makeText(ReportActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error saving data", e);
            Toast.makeText(ReportActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
        });
    }

    private void calculateWeeklyConsumption() {
        String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dailyData").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalWeeklyConsumption = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Double calories = snapshot.getValue(Double.class);
                    if (calories != null) {
                        totalWeeklyConsumption += calories;
                    }
                }

                // Display the total weekly consumption
                consumedCaloriesText.setText("Your weekly kcal intake is: " + totalWeeklyConsumption + " kcal");
                calculateBalanceWithWeeklyConsumption(totalWeeklyConsumption); // Optional: calculate balance
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error loading weekly consumption data", databaseError.toException());
            }
        });
    }
    private void calculateBalanceWithWeeklyConsumption(double totalWeeklyConsumption) {
        int totalWeeklyBurn = thisWeekPoints; // Assuming thisWeekPoints is already calculated

        if (totalWeeklyConsumption > totalWeeklyBurn) {
            double excessCalories = totalWeeklyConsumption - totalWeeklyBurn;
            double weightGain = excessCalories / kcalConst;
            resultText.setText(String.format("You are likely to gain %.2f kilos", weightGain));
        } else if (totalWeeklyConsumption < totalWeeklyBurn) {
            double deficitCalories = totalWeeklyBurn - totalWeeklyConsumption;
            double weightLoss = deficitCalories / kcalConst;
            resultText.setText(String.format("You are likely to lose %.2f kilos", weightLoss));
        } else {
            resultText.setText("You are in balance, no kilos expected to be gained or lost");
        }
    }
    private void loadDailyData() {
        String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("dailyData").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String day = snapshot.getKey();
                    Double calories = snapshot.getValue(Double.class);

                    if (calories != null) {
                        switch (day) {
                            case "Monday":
                                mondayHolder = calories;
                                break;
                            case "Tuesday":
                                tuesdayHolder = calories;
                                break;
                            case "Wednesday":
                                wednesdayHolder = calories;
                                break;
                            case "Thursday":
                                thursdayHolder = calories;
                                break;
                            case "Friday":
                                fridayHolder = calories;
                                break;
                            case "Saturday":
                                saturdayHolder = calories;
                                break;
                            case "Sunday":
                                sundayHolder = calories;
                                break;
                        }
                    }
                }
                updateBarChart(currentUser.getUid());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Error loading data", databaseError.toException());
            }
        });
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
    private void calculateDays(String userId){
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                Boolean monday = documentSnapshot.getBoolean("isMondayEligible");
                if (monday == true){
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean tuesday = documentSnapshot.getBoolean("isTuesdayEligible");
                if (tuesday == true) {
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean wednesday = documentSnapshot.getBoolean("isWednesdayEligible");
                if (wednesday == true) {
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean thursday = documentSnapshot.getBoolean("isThursdayEligible");
                if (thursday == true) {
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean friday = documentSnapshot.getBoolean("isFridayEligible");
                if (friday == true) {
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean saturday = documentSnapshot.getBoolean("isSaturdayEligible");
                if (saturday == true) {
                    countDay++;
                }
            }
            if (documentSnapshot.exists()) {
                Boolean Sunday = documentSnapshot.getBoolean("isSundayEligible");
                if (Sunday == true) {
                    countDay++;
                }
            }
        });
    }

    //it gives a burns kcal message

    private void setTexts(String userId, String reportType) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        calculateDays(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Number pointsNumber = documentSnapshot.getLong("points");
                if (pointsNumber != null) {
                    int points = pointsNumber.intValue();
                    caloriesText.setText("Your " + reportType + " kcal burn is: " + points * 3 + " kcal");
                } else {
                    caloriesText.setText("Your " + reportType + " kcal burn is: 0");
                }
                consumedCaloriesText.setText("Your " + reportType + " kcal intake is: " + 6400 + " kcal");
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

    private void updateBarChart(String userId) {
        List<BarEntry> intakeEntries = new ArrayList<>();
        List<BarEntry> burnEntries = new ArrayList<>();

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                pointsA = documentSnapshot.getLong("points");
                if (pointsA != null && countDay > 0) {
                    int points = pointsA.intValue();
                        intakeEntries.add(new BarEntry(0,(float) mondayHolder));
                        if (documentSnapshot.getBoolean("isMondayEligible")) {
                            burnEntries.add(new BarEntry(0, points* 3 / countDay));
                    }
                        else{
                            burnEntries.add(new BarEntry(0, 0));

                        }
                    intakeEntries.add(new BarEntry(1, (float) tuesdayHolder));
                    if (documentSnapshot.getBoolean("isTuesdayEligible")) {
                        burnEntries.add(new BarEntry(1, points * 3/ countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(1, 0));

                    }
                    intakeEntries.add(new BarEntry(2, (float) wednesdayHolder));
                    if (documentSnapshot.getBoolean("isWednesdayEligible")) {
                        burnEntries.add(new BarEntry(2, points * 3/ countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(2, 0));

                    }
                    intakeEntries.add(new BarEntry(3, (float) thursdayHolder));
                    if (documentSnapshot.getBoolean("isThursdayEligible")) {
                        burnEntries.add(new BarEntry(3, points * 3 / countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(3, 0));

                    }
                    intakeEntries.add(new BarEntry(4, (float) fridayHolder));
                    if (documentSnapshot.getBoolean("isFridayEligible")) {
                        burnEntries.add(new BarEntry(4, points * 3/ countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(4, 0));

                    }
                    intakeEntries.add(new BarEntry(5, (float) saturdayHolder));
                    if (documentSnapshot.getBoolean("isSaturdayEligible")) {
                        burnEntries.add(new BarEntry(5, points *3 / countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(5, 0));

                    }
                    intakeEntries.add(new BarEntry(6, (float) sundayHolder));
                    if (documentSnapshot.getBoolean("isSundayEligible")) {
                        burnEntries.add(new BarEntry(6, points * 3 / countDay));
                    }
                    else{
                        burnEntries.add(new BarEntry(6, 0));

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
                } else {
                    Log.e("ReportActivity", "Invalid data: pointsA=" + pointsA + ", countDay=" + countDay);
                }
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
        });
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

    private void showSelectDayDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_day); // Assuming you have a separate layout for the dialog

        final Spinner daySpinner = dialog.findViewById(R.id.day_spinner);
        final Button okButton = dialog.findViewById(R.id.ok_button);

        if (daySpinner == null || okButton == null) {
            Log.e("ReportActivity", "Spinner or Button is not properly initialized. Check layout.");
            return; // Handle the error accordingly
        }

        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, daysOfWeek);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedDay = (String) daySpinner.getSelectedItem();
                Button selectDayButton = findViewById(R.id.selectDayButton);
                selectDayButton.setText(selectedDay);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}