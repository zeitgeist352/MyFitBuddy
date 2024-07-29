package com.nutrition;

import java.util.ArrayList;

public class NutrientData {

    //defining the static variable
    static ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();

    //it adds some nutrient samples
    public NutrientData()
    {
        nutrients.add( new Nutrient("Apple", 52, 0.3, 14, 0.2, 100));
        nutrients.add( new Nutrient("Banana", 89, 1.1, 23, 0.3, 100));
        nutrients.add( new Nutrient("Orange", 47, 0.9, 12, 0.1, 100));
        nutrients.add( new Nutrient("Watermelon", 30, 0.6, 8, 0.2, 100));
        nutrients.add( new Nutrient("Carrot", 41, 0.9, 10, 0.2, 100));
        nutrients.add( new Nutrient("Kidney Beans", 127, 8.7, 22, 0.5, 100));
        nutrients.add( new Nutrient("Oats", 389, 16.9, 66, 6.9, 100));
        nutrients.add( new Nutrient("Whole Milk", 61, 3.2, 5, 3.3, 100));
        nutrients.add( new Nutrient("Brown Rice", 111, 2.6, 23, 0.9, 100));
        nutrients.add( new Nutrient("Yogurt", 59, 10, 3.6, 0.4, 100));
        nutrients.add( new Nutrient("Chicken Breast", 165, 31, 0, 3.6, 100));
        nutrients.add( new Nutrient("Soy Milk", 33, 3, 1.6, 1.6, 100));
        nutrients.add( new Nutrient("Beef Steak", 271, 25, 0, 19, 100));
        nutrients.add( new Nutrient("Shrimp", 99, 24, 0, 0.3, 100));
        nutrients.add( new Nutrient("Salmon", 206, 22, 0, 13, 100));
        nutrients.add( new Nutrient("Turkey Breast", 135, 30, 0, 1, 100));
        nutrients.add( new Nutrient("Lentils", 116, 9, 20, 0.4, 100));
    }

    //it returns the nutrient with name
    public static Nutrient getNutrient(String name) {

        Nutrient nt = new Nutrient(name, 0, 0, 0, 0, 0);
        for (Nutrient nutrient : nutrients) {
            if (name.equals(nutrient.getName())) {
                nt = nutrient;
            }
        }
        return nt;
    }

}
