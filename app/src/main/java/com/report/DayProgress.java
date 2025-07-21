package com.report;

public class DayProgress {
    private String day;
    private int burnedCalories;
    private int consumedCalories;

    public DayProgress() {
        // Default constructor required for Firebase
    }

    public DayProgress(String day, int burnedCalories, int consumedCalories) {
        this.day = day;
        this.burnedCalories = burnedCalories;
        this.consumedCalories = consumedCalories;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(int burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public int getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(int consumedCalories) {
        this.consumedCalories = consumedCalories;
    }
}
