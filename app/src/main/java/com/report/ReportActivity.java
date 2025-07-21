package com.report;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MainActivity;
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

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.report.DayProgressAdapter;
import com.report.DayProgress;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.report.DayProgressAdapter;
import com.report.DayProgress;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Toolbar toolbar = findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish()); // Go back to Main Menu

        recyclerView = findViewById(R.id.progressRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        caloriesText = findViewById(R.id.caloriesText);
        consumedCaloriesText = findViewById(R.id.consumedCaloriesText);
        resultText = findViewById(R.id.resultText);
        buttonClear = findViewById(R.id.buttonClear);
        buttonAdd = findViewById(R.id.buttonAdd);
        selectDayButton = findViewById(R.id.selectDayButton);

        progressList = new ArrayList<>();
        adapter = new DayProgressAdapter(progressList);
        recyclerView.setAdapter(adapter);

        userRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        loadData();

        buttonAdd.setOnClickListener(v -> addDummyEntry());

        buttonClear.setOnClickListener(v -> {
            userRef.child("Report").removeValue();
            progressList.clear();
            adapter.notifyDataSetChanged();
            updateSummary();
        });

        selectDayButton.setOnClickListener(v -> showDaySelectionDialog());
    }

    private void loadData() {
        userRef.child("Report").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressList.clear();
                for (DataSnapshot daySnap : snapshot.getChildren()) {
                    String day = daySnap.getKey();
                    int burned = daySnap.child("burnedCalories").getValue(Integer.class) != null ? daySnap.child("burnedCalories").getValue(Integer.class) : 0;
                    int consumed = daySnap.child("consumedCalories").getValue(Integer.class) != null ? daySnap.child("consumedCalories").getValue(Integer.class) : 0;
                    progressList.add(new DayProgress(day, burned, consumed));
                }
                adapter.notifyDataSetChanged();
                updateSummary();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Select a day first.", Toast.LENGTH_SHORT).show();
            return;
        }

        int burned = (int) (Math.random() * 700);
        int consumed = (int) (Math.random() * 900);

        Map<String, Object> data = new HashMap<>();
        data.put("burnedCalories", burned);
        data.put("consumedCalories", consumed);

        userRef.child("Report").child(selectedDay).setValue(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Data added.", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
    }

    private void showDaySelectionDialog() {
        final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Select a Day")
                .setItems(days, (dialog, which) -> {
                    selectedDay = days[which];
                    Toast.makeText(this, "Selected: " + selectedDay, Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }
}
