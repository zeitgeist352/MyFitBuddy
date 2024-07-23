package com.get_info_activites;

import android.content.Context;
import android.content.SharedPreferences;

import com.algorithm.Exercises;
import com.algorithm.Tester;
import com.algorithm.WorkoutPrograms;

import java.io.Serializable;
import java.util.ArrayList;

public class UserInfoHolder implements Serializable {

    private double power;

    private String gender;
    private boolean chest;
    private boolean back;
    private boolean arm;
    private boolean leg;
    private int age;
    private int weight;
    private int height;
    private int hipCircum;
    private int armCircum;
    private int legCircum;
    private int waistCircum;

    private boolean isMondayEligible;
    private boolean isTuesdayEligible;
    private boolean isWednesdayEligible;
    private boolean isThursdayEligible;
    private boolean isFridayEligible;
    private boolean isSaturdayEligible;
    private boolean isSundayEligible;

    private String purpose;
    private String bodyType;
    private int pushupCount;

    private int numberGoingGym;

    private boolean[] days;

    private double userPoint;
    private double ibm;

    private int icon;


    private ArrayList<ArrayList<Exercises>> program;

    public UserInfoHolder(String gender, boolean chest, boolean back, boolean arm, boolean leg,
                          int age, int weight, int height, boolean isMondayEligible,
                          boolean isTuesdayEligible, boolean isWednesdayEligible,
                          boolean isThursdayEligible, boolean isFridayEligible,
                          boolean isSaturdayEligible, boolean isSundayEligible,
                          String purpose, String bodyType, int pushupCount) {
        Tester t = new Tester();
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.days = new boolean[7];
        this.purpose = purpose;
        this.bodyType = bodyType;
        this.pushupCount = pushupCount;
        this.userPoint = 2;
        this.numberGoingGym = 0;
        this.power = 2;
        this.icon = 1;
    }

    public void calculateIbm()
    {
        System.out.println("weight heigth respectively: *************** : " + this.weight + " , " + this.height);
        this.ibm = (double) this.getWeight() / ((double) (this.getHeight() * this.getHeight()) / 10000);
        System.out.println("ibm: *************** : " + this.ibm);
    }

    public void setDays(int index, boolean isEligible)
    {
        this.days[index] = isEligible;
        if (isEligible)
        {
            this.numberGoingGym++;
        }
    }

    public void setIcon(int i)
    {
        this.icon = i;
    }

    public String getGender() {
        return gender;
    }

    public boolean isChest() {
        return chest;
    }

    public boolean isBack() {
        return back;
    }

    public boolean isArm() {
        return arm;
    }

    public boolean isLeg() {
        return leg;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getHipCircum(){
        return hipCircum;
    }

    public int getArmCircum(){
        return armCircum;
    }

    public int getLegCircum(){
        return legCircum;
    }

    public int getWaistCircum(){
        return waistCircum;
    }


    public boolean isMondayEligible() {
        return isMondayEligible;
    }

    public boolean isTuesdayEligible() {
        return isTuesdayEligible;
    }

    public boolean isWednesdayEligible() {
        return isWednesdayEligible;
    }

    public boolean isThursdayEligible() {
        return isThursdayEligible;
    }

    public boolean isFridayEligible() {
        return isFridayEligible;
    }

    public boolean isSaturdayEligible() {
        return isSaturdayEligible;
    }

    public boolean isSundayEligible() {
        return isSundayEligible;
    }

    public String getPurpose() {
        return purpose;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public String getBodyType() {
        return bodyType;
    }

    public int getPushupCount() {
        return pushupCount;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setChest(boolean chest) {
        this.chest = chest;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public void setArm(boolean arm) {
        this.arm = arm;
    }

    public void setLeg(boolean leg) {
        this.leg = leg;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setHipCircum(int hipCircum){
        this.hipCircum = hipCircum;
    }

    public void setArmCircum(int armCircum){
        this.armCircum = armCircum;
    }
    public void setWaistCircum(int waistCircum){
        this.waistCircum = waistCircum;
    }
    public void setLegCircum(int legCircum){
        this.legCircum = legCircum;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public void setPushupCount(int pushupCount) {
        this.pushupCount = pushupCount;
    }

    public void printPrefDays(){
        System.out.println(numberGoingGym);
    }

    /**
     * returns the days in which the user goes to gym
     * if days[0] is true it means the user goes to gym on monday.     *
     * @return the days of a week as a boolean arraylist
     */
    public boolean[] getDays()
    {
        return this.days;
    }

    public void saveExerciseDaysToThePhone(Context context, boolean[] days) {
        String PREF_NAME = "ExerciseDays";
        String KEY_PREFIX = "day_";
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < days.length; i++) {
            editor.putBoolean(KEY_PREFIX + i, days[i]);
        }
        editor.apply();
    }

    /**
     * We won't use this when first issuing the program, it can be used in feedback later.
     * @param n increases the user's power by n
     */
    public void increasePower(double n)
    {
        this.power += n;
    }

    /**
     * It evaluates the power of the user according to their body information.
     */
    public void setPower()
    {
        //increases the power according to bmi.
        //if bmi is equal to 23.5 increment will be max.
        this.power = this.power + ((5 - Math.abs(ibm - 23.5)) / 5);

        System.out.println("AFTER IBM** " + this.power);

        //increases the power according to body type.
        if (this.bodyType.equals("muscular"))
        {
            this.power += 1.7;
        }
        else if(this.bodyType.equals("normal"))
        {
            this.power += 0.7;
        }
        else if (this.bodyType.equals("thin"))
        {
            this.power += 0.3;
        }
        else if (this.bodyType.equals("fat"))
        {
            this.power += 0.5;
        }

        System.out.println("AFTER BODYTYPE " + this.power);

        //increases the power according to pushup count.
        this.power += this.pushupCount / 9.0;

        System.out.println("AFTER PUSHUPCOUNT " + this.power);

        if (this.gender.equals("male"))
        {
            this.power += 0.4;
        }
        if (this.power < 1)
        {
            this.power = 1.1;
        }
        System.out.println("*********FINAL " + this.power);

        this.generateProgram();
    }


    public void generateProgram()
    {
        WorkoutPrograms w = new WorkoutPrograms();

        if (this.purpose.equals("buildMuscles"))
        {
            this.program = w.getBuildMusclePrograms()[numberGoingGym - 2];
            Tester.generateMuscleProgram(this.program, this.power, false);
        }
        else if (this.purpose.equals("loseWeight"))
        {
            this.program = w.getCardioPrograms()[numberGoingGym - 2];
            Tester.generateCardioWorkoutProgram(this.program, this.power, false);
        }
        else if (this.purpose.equals("maintainForm"))
        {
            this.program = w.getMixedPrograms()[numberGoingGym - 2];
            Tester.generateMuscleProgram(this.program, this.power, true);
            Tester.generateCardioWorkoutProgram(this.program, this.power, true);
        }

        if(chest)
        {
            Tester.addChestTargetExercises(this.program, this.power);
        }
        if(back)
        {
            Tester.addBackTargetExercises(this.program, this.power);
        }
        if(arm)
        {
            Tester.addArmTargetExercises(this.program, this.power);
        }
        if(leg)
        {
            Tester.addLegTargetExercises(this.program, this.power);
        }


    }

    public void setMondayEligible(boolean mondayEligible) {
        isMondayEligible = mondayEligible;
    }

    public void setTuesdayEligible(boolean tuesdayEligible) {
        isTuesdayEligible = tuesdayEligible;
    }

    public void setWednesdayEligible(boolean wednesdayEligible) {
        isWednesdayEligible = wednesdayEligible;
    }
    public void setThursdayEligible(boolean thursdayEligible) {
        isThursdayEligible = thursdayEligible;
    }
    public void setFridayEligible(boolean fridayEligible) {
        isFridayEligible = fridayEligible;
    }

    public void setSaturdayEligible(boolean saturdayEligible) {
        isSaturdayEligible = saturdayEligible;
    }
    public void setSundayEligible(boolean sundayEligible) {
        isSundayEligible = sundayEligible;
    }

    public ArrayList<ArrayList<Exercises>> getProgram() {
        return program;
    }

    public void regenerateWorkoutProgram() // when the user clicks "regenerate program" in settings
    {
        generateProgram();
    }

}





