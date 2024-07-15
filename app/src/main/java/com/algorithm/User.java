package com.algorithm;

import java.util.ArrayList;

public class User {
    
    private double strength;
    private int dayCount = 0;
    private WorkoutPrograms s = new WorkoutPrograms();
    private ArrayList<ArrayList<Exercises>> program;

    public void resetProg()
    {
        this.program = null;
    }

    public User()
    {
        this.strength = 1;
    }

    public void increaseStrength(double artis)
    {
        this.strength = this.strength + artis;
    }

    public void setDayCount(int n)
    {
        this.dayCount = n;
    }

    public int getDayCount()
    { 
        return this.dayCount;
    }

    public double getStrength()
    {
        return this.strength;
    }

    public ArrayList<ArrayList<Exercises>> programiDondur()
    {
        return this.program;
    }


    public void displayProgramme()
    {
        for (int i = 0; i < program.size(); i++)
        {
            System.out.println("--------- " + (i + 1) + ". Day ---------");
            for (int j = 0; j < program.get(i).size(); j++)
            {
                System.out.println(program.get(i).get(j));
            }
        }
    }

}
