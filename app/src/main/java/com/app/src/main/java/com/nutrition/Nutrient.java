package com.nutrition;

public class Nutrient {

    private String name;
    private double proteinsPer100Gram;
    private double carbsPer100Gram;
    private double fatsPer100Gram;
    private double caloriesPer100Gram;
    private double grams;

    public Nutrient(String name , double proteins, double carbs, double fat, double calories)
    {
        this.name = name;
        this.proteinsPer100Gram = proteins;
        this.carbsPer100Gram = carbs;
        this.fatsPer100Gram = fat;
        this.caloriesPer100Gram = calories;
    }

    public String getName()
    {
        return this.name;
    }

    public double getProteins()
    {
        return this.proteinsPer100Gram;
    }

    public double getCarbs()
    {
        return this.carbsPer100Gram;
    }

    public double getFats()
    {
        return this.fatsPer100Gram;
    }

    public double getCalories()
    {
        return this.caloriesPer100Gram;
    }

    public double getGrams()
    {
        return this.grams;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setProteins(double proteins)
    {
        this.proteinsPer100Gram = proteins;
    }

    public void setCarbs(double carbs)
    {
        this.carbsPer100Gram = carbs;
    }

    public void setFats(double fats)
    {
        this.fatsPer100Gram = fats;
    }

    public void setCalories(double calories)
    {
        this.caloriesPer100Gram = calories;
    }

    public void setGrams(double grams)
    {
        this.grams = grams;
    }

    public String toString()
    {
        return this.name + " Amount: " + this.grams + " grams";
    }
}
