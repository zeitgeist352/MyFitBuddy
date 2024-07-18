package com.nutrition;

import java.util.HashMap;
import java.util.Map;

public class NutrientData {
    private static final Map<String, Nutrient> nutrientMap = new HashMap<>();

    static {
        nutrientMap.put("Apple", new Nutrient("Apple", 52, 0, 14, 0, 100));
        nutrientMap.put("Banana", new Nutrient("Banana", 96, 1, 27, 0, 100));
    }

    public static Nutrient getNutrient(String name) {
        return nutrientMap.getOrDefault(name, new Nutrient(name, 0, 0, 0, 0, 0));
    }
}