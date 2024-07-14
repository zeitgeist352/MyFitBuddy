package com.nutrition;

public class NutrientActivity extends AppCompatActivity {

    private EditText foodNameInput;
    private Button addFoodButton;
    private EditText proteinInput;
    private EditText carbInput;
    private EditText fatInput;
    private EditText caloriesInput;
    private Button addFoodFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient);

        foodNameInput = findViewById(R.id.foodNameInput);
        addFoodButton = findViewById(R.id.addFoodButton);
        proteinInput = findViewById(R.id.proteinInput);
        carbInput = findViewById(R.id.carbInput);
        fatInput = findViewById(R.id.fatInput);
        caloriesInput = findViewByID(R.id.caloriesInput);

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = foodNameInput.getText().toString();
                double proteins = Double.parseDouble(proteinInput.getText());
                double carbs = Double.parseDouble(carbInput.getText());
                double fats = Double.parseDouble(fatInput.getText());
                double calories = Double.parseDouble(caloriesInput.getText());
                Nutrient nutrient1 = new Nutrient();
                foodNameInput.setText("");
                proteinInput.setText(Double.toString("0"));
                carbInput.setText(Double.toString("0"));
                fatInput.setText(Double.toString("0"));
                caloriesInput.setText(Double.toString("0"));

                if (!foodName.isEmpty()) {
                    // Here you can add code to save the food name to a database or a list
                    nutrient1.setName(foodName);
                } 

                if (!proteinInput.getText().isEmpty()) {
                    // Here you can add code to save the food name to a database or a list
                    nutrient1.setProteins(proteins);
                } 

                if (!carbInput.isEmpty()) {
                    // Here you can add code to save the food name to a database or a list
                    nutrient1.setCarbs(carbs);
                } 

                if (!fatInput.isEmpty()) {
                    // Here you can add code to save the food name to a database or a list
                    nutrient1.setFats(fats);
                } 

                if (!caloriesInput.isEmpty()) {
                    // Here you can add code to save the food name to a database or a list
                    nutrient1.setCalories(calories);
                } 
            }
        });
    }

}
