package com;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.exercises.ExerciseAdapter;
import com.exercises.ExerciseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.myfitbuddy.databinding.ActivityMainBinding;
import com.myfitbuddy.databinding.ActivityReportBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ReportActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private int thisWeekPoints = 0;
    private int lastWeekPoints = 0;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private ArrayList<ExerciseModel> exerciseList;
    private int goalpoints = 10000;


    private ActivityReportBinding binding;

    private ExerciseAdapter exerciseAdapter;
    TextView caloriesText;
    TextView daysToGoalText;
    TextView consumedCaloriesText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        super.onCreate(savedInstanceState);
        binding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbarReport.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(ReportActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        /*
        //to be declared change from view9
        //daysToGoalText = findViewById(R.id.textView9);
        */

        caloriesText = binding.caloriesText;
        consumedCaloriesText = binding.consumedCaloriesText;
        findCurrentUserPlace();
        setTexts(currentUser.getUid());
    }

    private void setTexts(String userId) {
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Users").document(userId);
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Retrieve and display name
                System.out.println("REPORT ACTIVITY");
                // Retrieve and display points
                Number pointsNumber = documentSnapshot.getLong("points");
                if (pointsNumber != null) {  // Check if the points data exists
                    int points = pointsNumber.intValue();
                    double n1 = (double) points / 30000.0;
                    double n2 = (double) points / 25000.0;

/*
                    String formattedN1 = String.format("%.6f", n1);
                    String formattedN2 = String.format("%.6f", n2);
*/
                    caloriesText.setText("Calories have burned so far: " + points * 3);

                    //burasi doldurulacak kalori intakei ile
                    //consumedCaloriesText.setText("Calories have consumed so far: " + 0);

                } else {
                    caloriesText.setText("Calories have burned so far: " + 0);; // Default to 0 if no points
                    consumedCaloriesText.setText("Calories have consumed so far: " + 0);
                }
            } else {
                Log.d("Error", "No such document with the current user id: " + userId);
            }
        }).addOnFailureListener(e -> Log.d(TAG, "Error fetching document", e));
    }
    private void findCurrentUserPlace() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("Users")
                    .orderBy("points", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            int currentUserRank = 0;
                            int totalUsers = documents.size();
                            for (int i = 0; i < documents.size(); i++) {
                                if (documents.get(i).getId().equals(userId)) {
                                    currentUserRank = i + 1; // Rank starts from 1
                                    break;
                                }
                            }
                            double percentage = 100.0 - ((double) currentUserRank / totalUsers * 100);
                            String message = "Congratulations! You have exercised more than " + String.format(Locale.getDefault(), "%.2f", percentage) + "% of Bilfit users.";
                            // Şimdi bu mesajı bir TextView'e yazdıralım. Örneğin:
                            daysToGoalText.setText(message);
                        } else {
                            Log.e(TAG, "Error getting documents: ", task.getException());
                        }
                    });
        }
    }

}