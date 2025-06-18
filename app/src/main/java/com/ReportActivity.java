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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

    // Array for X-axis labels
    private final String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};


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

        // Initialize chart settings
        setupBarChart();

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

        binding.buttonAdd.setOnClickListener(v -> {
            String day = String.valueOf(selectDayButton.getText());
            double calories = nutrientList.getTotalCalories();
            saveDataToFirebase(day, calories);

            // Update holders and invalidate chart after saving
            updateDayHolder(day, calories);
            barChart.invalidate(); // Invalidate to redraw the chart with new data
        });

        loadNutrientsFromDb();
        nutrientList = new NutrientList(new ArrayList<>());
        loadNutrientsFromDb(); // Called twice, consider if intended
        setTexts(currentUser.getUid(), reportType);
        updateBarChart(currentUser.getUid()); // Initial chart update
    }

    private void setupBarChart() {

        barChart.setBackgroundColor(Color.GRAY);
        barChart.getDescription().setEnabled(false); // No description text
        barChart.setPinchZoom(false); // Disable pinch zoom
        barChart.setDrawBarShadow(false); // No shadow for bars
        barChart.setDrawGridBackground(false); // No grid background

        // X-axis configuration
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false); // Do not draw vertical grid lines
        xAxis.setGranularity(1f); // Minimum interval between values on the axis
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days)); // Set custom labels
        xAxis.setTextSize(10f); // Increase text size for better readability
        xAxis.setLabelCount(days.length); // Ensure all labels are shown
        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);

        // Left Y-axis configuration
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // Start from 0
        leftAxis.setGranularity(100f); // Set granularity for Y-axis labels
        leftAxis.setTextSize(10f); // Increase text size for better readability
        leftAxis.setDrawGridLines(false); // Draw horizontal grid lines for better readability
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);

        // Right Y-axis configuration (disable)
        barChart.getAxisRight().setEnabled(false);

        // Legend configuration
        Legend legend = barChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setYOffset(10f);
        legend.setXOffset(10f);
        legend.setTextSize(12f); // Increase legend text size
    }

    private void updateDayHolder(String day, double calories) {
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
                consumedCaloriesText.setText("Your weekly kcal intake is: " + String.format("%.0f", totalWeeklyConsumption) + " kcal");
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
            resultText.setText(String.format("You are likely to gain %.2f kilograms", weightGain));
        } else if (totalWeeklyConsumption < totalWeeklyBurn) {
            double deficitCalories = totalWeeklyBurn - totalWeeklyConsumption;
            double weightLoss = deficitCalories / kcalConst;
            resultText.setText(String.format("You are likely to lose %.2f kilograms", weightLoss));
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
                        updateDayHolder(day, calories); // Use the helper method
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

    private void calculateDays(String userId) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                countDay = 0; // Reset countDay before recalculating
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isMondayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isTuesdayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isWednesdayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isThursdayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isFridayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isSaturdayEligible"))) {
                    countDay++;
                }
                if (Boolean.TRUE.equals(documentSnapshot.getBoolean("isSundayEligible"))) {
                    countDay++;
                }
                // Call updateBarChart here to ensure `countDay` is updated before chart is drawn
                updateBarChart(userId);
            } else {
                Log.d("ReportActivity", "No user document found for calculateDays: " + userId);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document for calculateDays", e);
        });
    }


    //it gives a burns kcal message

    private void setTexts(String userId, String reportType) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        calculateDays(userId); // Ensure countDay is updated before setTexts
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Number pointsNumber = documentSnapshot.getLong("points");
                if (pointsNumber != null) {
                    int points = pointsNumber.intValue();
                    caloriesText.setText("Your " + reportType + " kcal burn is: " + points * 3 + " kcal");
                } else {
                    caloriesText.setText("Your " + reportType + " kcal burn is: 0");
                }
                // The consumedCaloriesText is updated by calculateWeeklyConsumption(),
                // so we don't need to set a static value here.
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
            resultText.setText(String.format("You are likely to gain %.2f kilograms", weightGain));
        } else if (totalConsumed < totalBurned) {
            double deficitCalories = totalBurned - totalConsumed;
            double weightLoss = deficitCalories / kcalConst;
            resultText.setText(String.format("You are likely to lose %.2f kilograms", weightLoss));
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
                // Ensure countDay is correctly calculated before using it
                calculateDays(userId); // Call this here to ensure `countDay` is fresh

                if (pointsA != null && countDay > 0) {
                    int points = pointsA.intValue();
                    float dailyBurn = (float) (points * 3.0 / countDay); // Calculate daily burn correctly as float

                    intakeEntries.add(new BarEntry(0, (float) mondayHolder));
                    burnEntries.add(new BarEntry(0, Boolean.TRUE.equals(documentSnapshot.getBoolean("isMondayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(1, (float) tuesdayHolder));
                    burnEntries.add(new BarEntry(1, Boolean.TRUE.equals(documentSnapshot.getBoolean("isTuesdayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(2, (float) wednesdayHolder));
                    burnEntries.add(new BarEntry(2, Boolean.TRUE.equals(documentSnapshot.getBoolean("isWednesdayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(3, (float) thursdayHolder));
                    burnEntries.add(new BarEntry(3, Boolean.TRUE.equals(documentSnapshot.getBoolean("isThursdayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(4, (float) fridayHolder));
                    burnEntries.add(new BarEntry(4, Boolean.TRUE.equals(documentSnapshot.getBoolean("isFridayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(5, (float) saturdayHolder));
                    burnEntries.add(new BarEntry(5, Boolean.TRUE.equals(documentSnapshot.getBoolean("isSaturdayEligible")) ? dailyBurn : 0f));

                    intakeEntries.add(new BarEntry(6, (float) sundayHolder));
                    burnEntries.add(new BarEntry(6, Boolean.TRUE.equals(documentSnapshot.getBoolean("isSundayEligible")) ? dailyBurn : 0f));


                    BarDataSet intakeDataSet = new BarDataSet(intakeEntries, "Calorie Intake");
                    intakeDataSet.setColor(Color.parseColor("#4CAF50")); // A more pleasant green
                    intakeDataSet.setValueTextColor(Color.BLACK);
                    intakeDataSet.setValueTextSize(9f);

                    BarDataSet burnDataSet = new BarDataSet(burnEntries, "Calorie Burn");
                    burnDataSet.setColor(Color.parseColor("#F44336")); // A more pleasant red
                    burnDataSet.setValueTextColor(Color.BLACK);
                    burnDataSet.setValueTextSize(9f);

                    ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                    dataSets.add(intakeDataSet);
                    dataSets.add(burnDataSet);

                    BarData barData = new BarData(dataSets);
                    barData.setBarWidth(0.35f); // Adjust bar width
                    barData.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            // Only display values if they are greater than 0
                            return value > 0 ? String.format("%.0f", value) : "";
                        }
                    });


                    barChart.setData(barData);

                    // Grouped bars
                    float groupSpace = 0.3f;
                    float barSpace = 0.05f; // x2 dataset
                    // (barWidth + barSpace) * 2 + groupSpace = 1.00 -> interval per "group"
                    // (0.35 + 0.05) * 2 + 0.3 = 0.8 + 0.3 = 1.1 -> so (0.35 + 0.05)*2 = 0.8 which means 0.2 left to make it to 1.0 (0.2/7 days = 0.028)
                    barChart.groupBars(0f, groupSpace, barSpace);
                    barChart.getXAxis().setAxisMinimum(0f);
                    barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 7); // Set max for 7 days
                    barChart.invalidate(); // Refresh chart

                } else {
                    Log.e("ReportActivity", "Invalid data: pointsA=" + pointsA + ", countDay=" + countDay + ". Cannot draw chart.");
                    // Consider displaying a message to the user or clearing the chart if data is invalid
                    barChart.clear();
                    barChart.invalidate();
                }
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document for chart update", e);
        });
    }


    private void clearBarChart() {
        BarData barData = barChart.getData();
        if (barData != null) {
            for (IBarDataSet dataSet : barData.getDataSets()) {
                for (int i = 0; i < dataSet.getEntryCount(); i++) {
                    BarEntry entry = dataSet.getEntryForIndex(i);
                    entry.setY(0);
                }
            }
            // Also reset the internal holder values to 0
            mondayHolder = 0;
            tuesdayHolder = 0;
            wednesdayHolder = 0;
            thursdayHolder = 0;
            fridayHolder = 0;
            saturdayHolder = 0;
            sundayHolder = 0;

            // Clear data from Firebase as well for a complete reset
            String userId = currentUser.getUid();
            DatabaseReference dailyDataRef = FirebaseDatabase.getInstance().getReference("dailyData").child(userId);
            dailyDataRef.removeValue().addOnSuccessListener(aVoid -> {
                Toast.makeText(ReportActivity.this, "Bar chart and daily data cleared", Toast.LENGTH_SHORT).show();
                // After clearing, re-fetch data or update UI to reflect the cleared state
                calculateWeeklyConsumption(); // Recalculate weekly consumption (should be 0)
                calcBalance(thisWeekPoints); // Recalculate balance
                barChart.invalidate(); // Redraw chart
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Failed to clear daily data from Firebase", e);
                Toast.makeText(ReportActivity.this, "Failed to clear daily data", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Bar chart is already empty", Toast.LENGTH_SHORT).show();
        }
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

        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}; // Start with Monday as per chart order
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