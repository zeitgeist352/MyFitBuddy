package com.nutrition;

public class Nutrient {
    private String name;
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    private int grams;

    public Nutrient(String name, int calories, int protein, int carbs, int fat, int grams) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.grams = grams;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories * grams / 100;
    }

    public int getProtein() {
        return protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFat() {
        return fat;
    }
    public int getGrams(){
        return grams;
    }
}