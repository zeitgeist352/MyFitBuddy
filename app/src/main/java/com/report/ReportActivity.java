package com.report;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.myfitbuddy.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DayProgressAdapter adapter;
    private List<DayProgress> progressList;

    private TextView caloriesText, consumedCaloriesText, resultText;
    private Button buttonClear, buttonAdd, selectDayButton;

    private DatabaseReference userRef;
    private String selectedDay = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Use built-in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Weekly Report");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enables back arrow
        }

       /* // Toolbar back navigation
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
*/
        // Firebase reference
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // View initialization
        recyclerView = findViewById(R.id.progressRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        caloriesText = findViewById(R.id.caloriesText);
        consumedCaloriesText = findViewById(R.id.consumedCaloriesText);
        resultText = findViewById(R.id.resultText);
        buttonClear = findViewById(R.id.buttonClear);
        buttonAdd = findViewById(R.id.buttonAdd);
        selectDayButton = findViewById(R.id.selectDayButton);

        // RecyclerView setup
        progressList = new ArrayList<>();
        adapter = new DayProgressAdapter(progressList);
        recyclerView.setAdapter(adapter);

        // Load existing data
        loadData();

        // Add dummy data
        buttonAdd.setOnClickListener(v -> addDummyEntry());

        // Clear all data
        buttonClear.setOnClickListener(v -> {
            userRef.child("Report").removeValue();
            progressList.clear();
            adapter.notifyDataSetChanged();
            updateSummary();
            Toast.makeText(this, "Report cleared.", Toast.LENGTH_SHORT).show();
        });

        // Day selector
        selectDayButton.setOnClickListener(v -> showDaySelectionDialog());
    }

    private void loadData() {
        userRef.child("Report").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressList.clear();
                for (DataSnapshot daySnap : snapshot.getChildren()) {
                    String day = daySnap.getKey();

                    Number burnedVal = daySnap.child("burnedCalories").getValue(Number.class);
                    Number consumedVal = daySnap.child("consumedCalories").getValue(Number.class);

                    int burned = burnedVal != null ? burnedVal.intValue() : 0;
                    int consumed = consumedVal != null ? consumedVal.intValue() : 0;

                    progressList.add(new DayProgress(day, burned, consumed));
                }
                adapter.notifyDataSetChanged();
                updateSummary();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSummary() {
        int totalBurned = 0;
        int totalConsumed = 0;

        for (DayProgress dp : progressList) {
            totalBurned += dp.getBurnedCalories();
            totalConsumed += dp.getConsumedCalories();
        }

        int balance = totalConsumed - totalBurned;

        caloriesText.setText("Calories Burned: " + totalBurned);
        consumedCaloriesText.setText("Calories Consumed: " + totalConsumed);
        resultText.setText("Balance Result: " + balance);
    }

    private void addDummyEntry() {
        if (selectedDay == null) {
            Toast.makeText(this, "Please select a day first.", Toast.LENGTH_SHORT).show();
            return;
        }

        int burned = (int) (Math.random() * 700);
        int consumed = (int) (Math.random() * 900);

        Map<String, Object> data = new HashMap<>();
        data.put("burnedCalories", burned);
        data.put("consumedCalories", consumed);

        userRef.child("Report").child(selectedDay).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Data added for " + selectedDay, Toast.LENGTH_SHORT).show();
                loadData();
            } else {
                Toast.makeText(this, "Failed to add data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDaySelectionDialog() {
        final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Select a Day")
                .setItems(days, (dialog, which) -> {
                    selectedDay = days[which];
                    Toast.makeText(this, "Selected: " + selectedDay, Toast.LENGTH_SHORT).show();
                })
                .create()
                .show();
    }


}
