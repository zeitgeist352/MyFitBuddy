package com.nutrition;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.R;
import com.myfitbuddy.databinding.ActivityNutrientBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NutrientActivity extends AppCompatActivity {

    private static final String TAG = "NutrientActivity";
    private TextView textViewCalories;
    private TextView textViewProtein;
    private TextView textViewCarbs;
    private TextView textViewFat;
    private Button buttonAddNutrient;
    private Button buttonSelectNutrient;
    private RecyclerView recyclerViewNutrientList;
    private Context context;

    private ActivityNutrientBinding binding;

    private NutrientAdapter nutrientAdapter;
    private ArrayList<Nutrient> nutrients;
    private NutrientList nutrientList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    private NutrientData nutrientData;
    private ArrayList<String> nutrientNames;

    private chartMonday mondayChart;
    private chartTuesday tuesdayChart;
    private chartWednesday wednesdayChart;
    private chartThursday thursdayChart;
    private chartFriday fridayChart;
    private chartSaturday saturdayChart;
    private chartSunday sundayChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNutrientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        nutrientData = new NutrientData();
        nutrientNames = new ArrayList<>();

        for (Nutrient nutrient : NutrientData.nutrients) {
            nutrientNames.add(nutrient.getName());
        }

        Log.d(TAG, "Nutrient names: " + nutrientNames.toString());


        binding.toolbarNutrient.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(NutrientActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        // Initialize views
        textViewCalories = findViewById(R.id.textView_nutrient_calories);
        textViewProtein = findViewById(R.id.textView_nutrient_protein);
        textViewCarbs = findViewById(R.id.textView_nutrient_carbs);
        textViewFat = findViewById(R.id.textView_nutrient_fat);
        buttonAddNutrient = findViewById(R.id.button_add_nutrient);
        buttonSelectNutrient = findViewById(R.id.button_select_nutrient);
        recyclerViewNutrientList = findViewById(R.id.recycler_view_nutrient_list);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        // Initialize Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("nutrients");

        // Initialize nutrient list and adapter
        nutrientList = new NutrientList(new ArrayList<>());
        nutrientAdapter = new NutrientAdapter(nutrientList.getNutrients());
        recyclerViewNutrientList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNutrientList.setAdapter(nutrientAdapter);

        // Load nutrients from Firebase
        loadNutrientsFromFirebase();

        nutrientAdapter.setOnItemClickListener(new NutrientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Nutrient nutrient) {
            }

            @Override
            public void onDeleteClick(Nutrient nutrient) {
                new AlertDialog.Builder(NutrientActivity.this)
                        .setTitle("Delete Nutrient").setMessage("Are you sure you want to delete \"" + nutrient.getName() + "\" nutrient?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Remove the nutrient from the list and update UI
                            nutrientList.removeNutrient(nutrient);
                            nutrientAdapter.notifyDataSetChanged();
                            updateNutrientInfo();
                            // Delete the nutrient from db
                            deleteNutrientFromFirebase(nutrient);
                        })
                        .setNegativeButton(android.R.string.no, null).setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });

        // Creates days for intaken calories chart
        mondayChart = new chartMonday();
        tuesdayChart = new chartTuesday();
        wednesdayChart = new chartWednesday();
        thursdayChart = new chartThursday();
        fridayChart = new chartFriday();
        saturdayChart = new chartSaturday();
        sundayChart = new chartSunday();

        // Set button click listener
        buttonAddNutrient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNutrientDialog();
            }
        });
        buttonSelectNutrient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectNutrientDialog();
            }
        });

        // Update nutrient information display
        updateNutrientInfo();
    }

    private void loadNutrientsFromFirebase() {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    nutrientList.getNutrients().clear(); // Clear the current list
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Nutrient nutrient = snapshot.getValue(Nutrient.class);
                        nutrientList.addNutrient(nutrient);
                    }
                    nutrientAdapter.notifyDataSetChanged();
                    updateNutrientInfo();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("Firebase", "Failed to load nutrients", databaseError.toException());
                }
            });
        }
    }
    private void showSelectNutrientDialog() {
        if (nutrientNames == null || nutrientNames.isEmpty()) {
            Log.e(TAG, "Nutrient names list is null or empty!");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_nutrient, null);
        builder.setView(dialogView);

        Spinner nutrientSpinner = dialogView.findViewById(R.id.nutrient_spinner);
        EditText gramsEditText = dialogView.findViewById(R.id.grams_edit_text);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nutrientNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nutrientSpinner.setAdapter(adapter);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String selectedNutrientName = nutrientSpinner.getSelectedItem().toString();
            String gramsText = gramsEditText.getText().toString();


            if (!gramsText.isEmpty()) {
                int grams = Integer.parseInt(gramsText);
                Nutrient selectedNutrient = NutrientData.getNutrient(selectedNutrientName);
                selectedNutrient.setGrams(grams);

                nutrientList.addNutrient(selectedNutrient);
                nutrientAdapter.notifyDataSetChanged();
                updateNutrientInfo();

                databaseReference.child(currentUser.getUid()).push().setValue(selectedNutrient);

                saveNutrientToFirestore(selectedNutrient);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

/* bu methoda gerek yok diger methodlar icersinde ayrı bir sekilde yazıldı
    private void showEnterGramsDialog(final Nutrient selectedNutrient) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enter_grams);

        final EditText editTextGrams = dialog.findViewById(R.id.editText_grams);
        Button buttonSave = dialog.findViewById(R.id.button_save_grams);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gramsStr = editTextGrams.getText().toString().trim();
                if (!gramsStr.isEmpty()) {
                    int grams = Integer.parseInt(gramsStr);
                    Nutrient nutrientWithGrams = NutrientData.getNutrient(selectedNutrient.getName());
                    nutrientWithGrams.setGrams(grams);

                    // Add the nutrient to the list and update UI
                    nutrientList.addNutrient(nutrientWithGrams);
                    nutrientAdapter.notifyDataSetChanged();
                    updateNutrientInfo();

                    // Save to Firebase
                    databaseReference.child(currentUser.getUid()).push().setValue(nutrientWithGrams);
                    saveNutrientToFirestore(nutrientWithGrams);

                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }
*/
    private void showAddNutrientDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_nutrient);

        final EditText editTextName = dialog.findViewById(R.id.editText_nutrient_name);
        final EditText editTextCalories = dialog.findViewById(R.id.editText_calories);
        final EditText editTextProtein = dialog.findViewById(R.id.editText_protein);
        final EditText editTextCarbs = dialog.findViewById(R.id.editText_carbs);
        final EditText editTextFat = dialog.findViewById(R.id.editText_fat);
        final EditText editTextGrams = dialog.findViewById(R.id.editText_grams);
        Button buttonSave = dialog.findViewById(R.id.button_save_nutrient);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                int calories = Integer.parseInt(editTextCalories.getText().toString().trim());
                int protein = Integer.parseInt(editTextProtein.getText().toString().trim());
                int carbs = Integer.parseInt(editTextCarbs.getText().toString().trim());
                int fat = Integer.parseInt(editTextFat.getText().toString().trim());
                int grams = Integer.parseInt(editTextGrams.getText().toString().trim());

                Nutrient nutrient = new Nutrient(name, calories, protein, carbs, fat, grams);

                databaseReference.child(currentUser.getUid()).push().setValue(nutrient);

                saveNutrientToFirestore(nutrient);

                nutrientList.addNutrient(nutrient);
                nutrientAdapter.notifyDataSetChanged();
                updateNutrientInfo();

                updateChartWithIntakeCalories(calories);

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void saveNutrientToFirestore(Nutrient nutrient) {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference userDocRef = db.collection("Users").document(userId).collection("nutrients").document();
            Map<String, Object> nutrientData = new HashMap<>();
            nutrientData.put("name", nutrient.getName());
            nutrientData.put("calories", nutrient.getCalories());
            nutrientData.put("protein", nutrient.getProtein());
            nutrientData.put("carbs", nutrient.getCarbs());
            nutrientData.put("fat", nutrient.getFat());
            nutrientData.put("grams", nutrient.getGrams());

            userDocRef.set(nutrientData)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Nutrient data saved successfully"))
                    .addOnFailureListener(e -> Log.d("Firebase", "Error saving nutrient data", e));

            updateChartWithIntakeCalories(nutrient.getCalories());
        }
    }

    private void deleteNutrientFromFirebase(Nutrient nutrient) {
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.child(userId).orderByChild("name").equalTo(nutrient.getName())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Firebase", "Failed to delete nutrient", databaseError.toException());
                        }
                    });

            db.collection("Users").document(userId).collection("nutrients")
                    .whereEqualTo("name", nutrient.getName())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot document : task.getResult()) {
                                document.getReference().delete()
                                        .addOnSuccessListener(aVoid -> Log.d("Firebase", "Nutrient deleted successfully"))
                                        .addOnFailureListener(e -> Log.e("Firebase", "Error deleting nutrient", e));
                            }
                        }
                    });
        }
    }

    private void updateNutrientInfo() {
        textViewCalories.setText("Calories: " + nutrientList.getTotalCalories() + " kcal");
        textViewProtein.setText("Protein: " + nutrientList.getTotalProteins() + " g");
        textViewCarbs.setText("Carbohydrates: " + nutrientList.getTotalCarbs() + " g");
        textViewFat.setText("Fat: " + nutrientList.getTotalFats() + " g");
    }

    private void updateChartWithIntakeCalories(double calories) {
        LocalDateTime today = LocalDateTime.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        switch (dayOfWeek) {
            case 1: // Monday
                mondayChart.setIntakedCalories(mondayChart.getIntakedCalories() + calories);
                break;
            case 2: // Tuesday
                tuesdayChart.setIntakedCalories(tuesdayChart.getIntakedCalories() + calories);
                break;
            case 3: // Wednesday
                wednesdayChart.setIntakedCalories(wednesdayChart.getIntakedCalories() + calories);
                break;
            case 4: // Thursday
                thursdayChart.setIntakedCalories(thursdayChart.getIntakedCalories() + calories);
                break;
            case 5: // Friday
                fridayChart.setIntakedCalories(fridayChart.getIntakedCalories() + calories);
                break;
            case 6: // Saturday
                saturdayChart.setIntakedCalories(saturdayChart.getIntakedCalories() + calories);
                break;
            case 7: // Sunday
                sundayChart.setIntakedCalories(sundayChart.getIntakedCalories() + calories);
                break;
        }
    }
}
