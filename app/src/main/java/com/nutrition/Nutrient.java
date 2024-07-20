package com.nutrition;

public class Nutrient {
    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;
    private double grams;

    public Nutrient() {
        // Default constructor required for calls to DataSnapshot.getValue(Nutrient.class)
    }

    public Nutrient(String name, double calories, double protein, double carbs, double fat, double grams) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.grams = grams;
    }

    // Getters and setters
    public String getName() { return name; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbs() { return carbs; }
    public double getFat() { return fat; }
    public double getGrams() { return grams; }

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