package com.nutrition;

import java.util.ArrayList;

public class NutrientData {

    //defining the static variable
    static ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();

    //it adds some nutrient samples
    public NutrientData()
    {
        //meat
        nutrients.add(new Nutrient("Chicken Breast", 165, 31, 0, 3.6, 100));
        nutrients.add(new Nutrient("Turkey Breast", 135, 30, 0, 1, 100));
        nutrients.add(new Nutrient("Beef Steak", 271, 25, 0, 19, 100));
        nutrients.add(new Nutrient("Shrimp", 99, 24, 0, 0.3, 100));
        nutrients.add(new Nutrient("Salmon", 206, 22, 0, 13, 100));
        nutrients.add(new Nutrient("Tuna", 132, 28, 0, 1.3, 100));
        nutrients.add(new Nutrient("Egg (Boiled)", 155, 13, 1.1, 11, 100));
        nutrients.add(new Nutrient("Lamb", 294, 25, 0, 21, 100));
        nutrients.add(new Nutrient("Pork Chop", 231, 22, 0, 15, 100));
        nutrients.add(new Nutrient("Duck Meat", 337, 27, 0, 28, 100));
        //dairy
        nutrients.add(new Nutrient("Whole Milk", 61, 3.2, 5, 3.3, 100));
        nutrients.add(new Nutrient("Soy Milk", 33, 3, 1.6, 1.6, 100));
        nutrients.add(new Nutrient("Yogurt", 59, 10, 3.6, 0.4, 100));
        nutrients.add(new Nutrient("Cottage Cheese", 98, 11, 3.4, 4.3, 100));
        nutrients.add(new Nutrient("Cheddar Cheese", 403, 25, 1.3, 33, 100));
        nutrients.add(new Nutrient("Almond Milk (Unsweetened)", 15, 0.5, 0.3, 1.2, 100));
        nutrients.add(new Nutrient("Tofu", 76, 8, 1.9, 4.8, 100));
        //vegs
        nutrients.add(new Nutrient("Carrot", 41, 0.9, 10, 0.2, 100));
        nutrients.add(new Nutrient("Broccoli", 34, 2.8, 7, 0.4, 100));
        nutrients.add(new Nutrient("Spinach", 23, 2.9, 3.6, 0.4, 100));
        nutrients.add(new Nutrient("Sweet Potato", 86, 1.6, 20, 0.1, 100));
        nutrients.add(new Nutrient("Green Peas", 81, 5.4, 14, 0.4, 100));
        nutrients.add(new Nutrient("Cucumber", 16, 0.7, 3.6, 0.1, 100));
        nutrients.add(new Nutrient("Eggplant", 25, 1, 6, 0.2, 100));
        nutrients.add(new Nutrient("Zucchini", 17, 1.2, 3.1, 0.3, 100));
        nutrients.add(new Nutrient("Cauliflower", 25, 1.9, 5, 0.3, 100));
        nutrients.add(new Nutrient("Bell Pepper", 31, 1, 6, 0.3, 100));
        //fruit
        nutrients.add(new Nutrient("Apple", 52, 0.3, 14, 0.2, 100));
        nutrients.add(new Nutrient("Banana", 89, 1.1, 23, 0.3, 100));
        nutrients.add(new Nutrient("Orange", 47, 0.9, 12, 0.1, 100));
        nutrients.add(new Nutrient("Watermelon", 30, 0.6, 8, 0.2, 100));
        nutrients.add(new Nutrient("Avocado", 160, 2, 9, 15, 100));
        nutrients.add(new Nutrient("Strawberries", 32, 0.7, 7.7, 0.3, 100));
        nutrients.add(new Nutrient("Blueberries", 57, 0.7, 14.5, 0.3, 100));
        nutrients.add(new Nutrient("Pineapple", 50, 0.5, 13, 0.1, 100));
        nutrients.add(new Nutrient("Mango", 60, 0.8, 15, 0.4, 100));
        nutrients.add(new Nutrient("Grapes", 69, 0.6, 18, 0.2, 100));
        //grains
        nutrients.add(new Nutrient("Oats", 389, 16.9, 66, 6.9, 100));
        nutrients.add(new Nutrient("Brown Rice", 111, 2.6, 23, 0.9, 100));
        nutrients.add(new Nutrient("Quinoa", 120, 4.4, 21, 1.9, 100));
        nutrients.add(new Nutrient("Lentils", 116, 9, 20, 0.4, 100));
        nutrients.add(new Nutrient("Kidney Beans", 127, 8.7, 22, 0.5, 100));
        nutrients.add(new Nutrient("Chickpeas", 164, 8.9, 27.4, 2.6, 100));
        nutrients.add(new Nutrient("White Rice", 130, 2.4, 28, 0.3, 100));
        nutrients.add(new Nutrient("Barley", 354, 12.5, 73, 2.3, 100));
        //nuts
        nutrients.add(new Nutrient("Almonds", 579, 21, 22, 50, 100));
        nutrients.add(new Nutrient("Peanut Butter", 588, 25, 20, 50, 100));
        nutrients.add(new Nutrient("Pumpkin Seeds", 559, 30, 11, 49, 100));
        nutrients.add(new Nutrient("Chia Seeds", 486, 17, 42, 31, 100));
        nutrients.add(new Nutrient("Walnuts", 654, 15, 14, 65, 100));
        nutrients.add(new Nutrient("Cashews", 553, 18, 30, 44, 100));
        nutrients.add(new Nutrient("Flaxseeds", 534, 18, 29, 42, 100));


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
