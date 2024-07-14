package com.nutrition;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myfitbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class NutrientActivity extends AppCompatActivity {

    private TextView textViewHead;
    private TextView textViewCalories;
    private TextView textViewProtein;
    private TextView textViewCarbs;
    private TextView textViewFat;
    private Button buttonAddNutrient;
    private RecyclerView recyclerViewNutrientList;

    private NutrientAdapter nutrientAdapter;
    private List<Nutrient> nutrientList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient);

        // Initialize views
        textViewCalories = findViewById(R.id.textView_nutrient_calories);
        textViewProtein = findViewById(R.id.textView_nutrient_protein);
        textViewCarbs = findViewById(R.id.textView_nutrient_carbs);
        textViewFat = findViewById(R.id.textView_nutrient_fat);
        buttonAddNutrient = findViewById(R.id.button_add_nutrient);
        recyclerViewNutrientList = findViewById(R.id.recycler_view_nutrient_list);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("nutrients");

        // Initialize nutrient list and adapter
        nutrientList = new ArrayList<>();
        nutrientAdapter = new NutrientAdapter(nutrientList);
        recyclerViewNutrientList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNutrientList.setAdapter(nutrientAdapter);

        // Set button click listener
        buttonAddNutrient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNutrientDialog();
            }
        });

        updateNutrientInfo();
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
                databaseReference.push().setValue(nutrient); //firebase
                nutrientList.add(nutrient);
                nutrientAdapter.notifyDataSetChanged();
                updateNutrientInfo();

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void updateNutrientInfo() {
        int totalCalories = 0;
        int totalProtein = 0;
        int totalCarbs = 0;
        int totalFat = 0;

        for (Nutrient nutrient : nutrientList) {
            totalCalories += nutrient.getCalories();
            totalProtein += nutrient.getProtein();
            totalCarbs += nutrient.getCarbs();
            totalFat += nutrient.getFat();
        }

        textViewCalories.setText("Calories: " + totalCalories + " kcal");
        textViewProtein.setText("Protein: " + totalProtein + " g");
        textViewCarbs.setText("Carbohydrates: " + totalCarbs + " g");
        textViewFat.setText("Fat: " + totalFat + " g");
    }
}