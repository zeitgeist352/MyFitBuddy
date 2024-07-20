package com.nutrition;

import java.util.ArrayList;

public class NutrientData {
    // private static final Map<String, Nutrient> nutrientMap = new HashMap<>();
    private static final Nutrient[] nutrients = new Nutrient[50];
    static {
        //Fruits
        nutrients[0] = new Nutrient("Apple", 52, 0.3, 14, 0.2, 100);
        nutrients[1] = new Nutrient("Banana", 89, 1.1, 23, 0.3, 100);
        nutrients[2] = new Nutrient("Orange", 47, 0.9, 12, 0.1, 100);
        nutrients[3] = new Nutrient("Strawberry", 32, 0.7, 7.7, 0.3, 100);
        nutrients[4] = new Nutrient("Grapes", 69, 0.7, 18, 0.2, 100);
        nutrients[5] = new Nutrient("Watermelon", 30, 0.6, 8, 0.2, 100);
        nutrients[6] = new Nutrient("Pineapple", 50, 0.5, 13, 0.1, 100);
        nutrients[7] = new Nutrient("Mango", 60, 0.8, 15, 0.4, 100);
        nutrients[8] = new Nutrient("Blueberry", 57, 0.7, 14, 0.3, 100);
        nutrients[9] = new Nutrient("Cherry", 50, 1, 12, 0.3, 100);
        nutrients[10] = new Nutrient("Carrot", 41, 0.9, 10, 0.2, 100);
        //Vegetables
        nutrients[11] = new Nutrient("Broccoli", 34, 2.8, 7, 0.4, 100);
        nutrients[12] = new Nutrient("Spinach", 23, 2.9, 3.6, 0.4, 100);
        nutrients[13] = new Nutrient("Tomato", 18, 0.9, 3.9, 0.2, 100);
        nutrients[14] = new Nutrient("Cucumber", 16, 0.7, 3.6, 0.1, 100);
        nutrients[15] = new Nutrient("Bell Pepper", 31, 1, 6, 0.3, 100);
        nutrients[16] = new Nutrient("Kale", 49, 4.3, 8.8, 0.9, 100);
        nutrients[17] = new Nutrient("Lettuce", 15, 1.4, 2.9, 0.2, 100);
        nutrients[18] = new Nutrient("Zucchini", 17, 1.2, 3.1, 0.3, 100);
        nutrients[19] = new Nutrient("Pumpkin", 26, 1, 7, 0.1, 100);
        nutrients[20] = new Nutrient("Quinoa", 120, 4.1, 21, 1.9, 100);
        //Grain
        nutrients[21] = new Nutrient("Brown Rice", 111, 2.6, 23, 0.9, 100);
        nutrients[22] = new Nutrient("Oats", 389, 16.9, 66, 6.9, 100);
        nutrients[23] = new Nutrient("Chickpeas", 164, 8.9, 27, 2.6, 100);
        nutrients[24] = new Nutrient("Lentils", 116, 9, 20, 0.4, 100);
        nutrients[25] = new Nutrient("Black Beans", 132, 8.9, 24, 0.5, 100);
        nutrients[26] = new Nutrient("Peas", 81, 5.4, 14, 0.4, 100);
        nutrients[27] = new Nutrient("Kidney Beans", 127, 8.7, 22, 0.5, 100);
        nutrients[28] = new Nutrient("Barley", 123, 2.3, 28, 0.4, 100);
        nutrients[29] = new Nutrient("Millet", 119, 3.5, 23, 1, 100);
        nutrients[30] = new Nutrient("Whole Milk", 61, 3.2, 5, 3.3, 100);
        //Dairy Products
        nutrients[31] = new Nutrient("Yogurt", 59, 10, 3.6, 0.4, 100);
        nutrients[32] = new Nutrient("Cheddar Cheese", 403, 24, 1.3, 33, 100);
        nutrients[33] = new Nutrient("Almond Milk", 17, 0.5, 0.3, 1.2, 100);
        nutrients[34] = new Nutrient("Soy Milk", 33, 3, 1.6, 1.6, 100);
        nutrients[35] = new Nutrient("Cottage Cheese", 98, 11, 3.4, 4.3, 100);
        nutrients[36] = new Nutrient("Mozzarella", 280, 28, 3, 17, 100);
        nutrients[37] = new Nutrient("Feta Cheese", 264, 14, 4.1, 21, 100);
        nutrients[38] = new Nutrient("Goat Cheese", 364, 22, 1, 30, 100);
        nutrients[39] = new Nutrient("Ricotta Cheese", 174, 11, 3, 13, 100);
        nutrients[40] = new Nutrient("Chicken Breast", 165, 31, 0, 3.6, 100);
        //Meat Products
        nutrients[41] = new Nutrient("Turkey Breast", 135, 30, 0, 1, 100);
        nutrients[42] = new Nutrient("Salmon", 206, 22, 0, 13, 100);
        nutrients[43] = new Nutrient("Tuna", 132, 29, 0, 1.3, 100);
        nutrients[44] = new Nutrient("Beef Steak", 271, 25, 0, 19, 100);
        nutrients[45] = new Nutrient("Pork Chop", 231, 23, 0, 15, 100);
        nutrients[46] = new Nutrient("Shrimp", 99, 24, 0, 0.3, 100);
        nutrients[47] = new Nutrient("Cod", 82, 18, 0, 0.7, 100);
        nutrients[48] = new Nutrient("Lamb", 294, 25, 0, 21, 100);
        nutrients[49] = new Nutrient("Tilapia", 96, 20, 0, 1.7, 100);

    }

    public static Nutrient getNutrient(String name) {
        //return nutrientMap.getOrDefault(name, new Nutrient(name, 0, 0, 0, 0, 0));
        Nutrient nt = new Nutrient(name, 0, 0, 0, 0, 0);
        for (Nutrient nutrient : nutrients) {
            if (name.equals(nutrient.getName())) {
                nt = nutrient;
            }
        }
        return nt;
    }

    public static ArrayList<Nutrient> definer(){
        ArrayList<Nutrient> list = new ArrayList<>();
        for (Nutrient nutrient : nutrients) {
            list.add(nutrient);
        }
        return list;
    }
}