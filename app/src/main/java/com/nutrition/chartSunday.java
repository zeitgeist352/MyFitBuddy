package com.nutrition;
public class chartSunday {

    //defining private variables
    private double burnedCalories;
    private double intakedCalories;

    //it sets the burned calories in sunday
    public void setBurnedCalories(double burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    //it sets the intaked calories in sunday
    public void setIntakedCalories(double intakedCalories) {
        this.intakedCalories = intakedCalories;
    }

    //it returns the burned calories in sunday
    public double getBurnedCalories() {
        return burnedCalories;
    }

    //it returns the intaked calories in sunday
    public double getIntakedCalories() {
        return intakedCalories;
    }
}
