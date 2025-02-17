package com.nutrition;

import java.util.ArrayList;

public class NutrientList {

    //defining private variable
    private ArrayList<Nutrient> nutrients;

    //constructor for nutrient list
    public NutrientList(ArrayList<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    // it returns the nutrients
    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }

    //it returns the total proteins
    public double getTotalProteins() {
        double totalProtein = 0;
        for (int i = 0; i < this.nutrients.size(); i++) {
            double proteinAmount = this.nutrients.get(i).getProtein();
            double grams = this.nutrients.get(i).getGrams();
            totalProtein += proteinAmount * grams / 100;
        }
        return totalProtein;
    }

    //it returns the total proteins
    public double getTotalCarbs() {
        double totalCarbs = 0;
        for (int i = 0; i < this.nutrients.size(); i++) {
            double carbsAmount = this.nutrients.get(i).getCarbs();
            double grams = this.nutrients.get(i).getGrams();
            totalCarbs += carbsAmount * grams / 100;
        }
        return totalCarbs;
    }

    //it returns the total fats
    public double getTotalFats() {
        double totalFats = 0;
        for (int i = 0; i < this.nutrients.size(); i++) {
            double fatAmount = this.nutrients.get(i).getFat();
            double grams = this.nutrients.get(i).getGrams();
            totalFats += fatAmount * grams / 100;
        }
        return totalFats;
    }

    //it returns the total calories
    public double getTotalCalories() {
        double totalCalories = 0;
        for (int i = 0; i < this.nutrients.size(); i++) {
            double calories = this.nutrients.get(i).getCalories();
            double grams = this.nutrients.get(i).getGrams();
            totalCalories += calories * grams / 100;
        }
        return totalCalories;
    }

    // it adds the nutrient
    public void addNutrient(Nutrient nutrient) {
        this.nutrients.add(nutrient);
    }

    // it removes the nutrient
    public void removeNutrient(Nutrient nutrient) {
        this.nutrients.remove(nutrient);
    }

    @Override
    public String toString() {
        StringBuilder nutritionList = new StringBuilder();
        for (int i = 0; i < this.nutrients.size(); i++) {
            nutritionList.append(this.nutrients.get(i).toString()).append("\n");
        }
        return nutritionList.toString();
    }
}