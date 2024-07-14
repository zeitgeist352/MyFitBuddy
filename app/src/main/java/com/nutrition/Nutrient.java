package com.nutrition;

public class Nutrient {
    private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int grams;

    public Nutrient() {
        // Default constructor required for calls to DataSnapshot.getValue(Nutrient.class)
    }

    public Nutrient(String name, int calories, int protein, int carbs, int fat, int grams) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.grams = grams;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public int getFat() { return fat; }
    public int getGrams() { return grams; }

    public void setName(String name) { this.name = name; }
    public void setCalories(int calories) { this.calories = calories; }
    public void setProtein(int protein) { this.protein = protein; }
    public void setCarbs(int carbs) { this.carbs = carbs; }
    public void setFat(int fat) { this.fat = fat; }
    public void setGrams(int grams) { this.grams = grams; }

    @Override
    public String toString() {
        return name + ": " + calories + " kcal, " + protein + " g protein, " + carbs + " g carbs, " + fat + " g fat, " + grams + " g";
    }
}