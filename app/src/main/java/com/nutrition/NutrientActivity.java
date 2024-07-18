package com.nutrition;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.R;
import com.myfitbuddy.databinding.ActivityNutrientBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NutrientActivity extends AppCompatActivity {

    private TextView textViewCalories;
    private TextView textViewProtein;
    private TextView textViewCarbs;
    private TextView textViewFat;
    private Button buttonAddNutrient;
    private RecyclerView recyclerViewNutrientList;

    private ActivityNutrientBinding binding;

    private NutrientAdapter nutrientAdapter;
    private NutrientList nutrientList;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNutrientBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        // Set button click listener
        buttonAddNutrient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNutrientDialog();
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
        }
    }

    private void updateNutrientInfo() {
        textViewCalories.setText("Calories: " + nutrientList.getTotalCalories() + " kcal");
        textViewProtein.setText("Protein: " + nutrientList.getTotalProteins() + " g");
        textViewCarbs.setText("Carbohydrates: " + nutrientList.getTotalCarbs() + " g");
        textViewFat.setText("Fat: " + nutrientList.getTotalFats() + " g");
    }

    Nutrient nutrient1 = new Nutrient("Chicken Breast" , 110, 23, 0, 1, 100);
    saveNutrientToFirestore(nutrient1);
}
