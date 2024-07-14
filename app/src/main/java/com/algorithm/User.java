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
/*
    public void focusChest()
    {
        ChestExercises h = new ChestExercises(0, null);

        for (int i = 0; i < program.size(); i++)
        {
            boolean flag = false;
            for (int j = 0; j < program.get(i).size(); j++)
            {
                if (program.get(i).get(j).getClass() == h.getClass())
                {
                    flag = true;
                }
            }
            if (flag)
            {
                this.program.get(i).add(Tester.getAvailableChestExercise(strength, program.get(i)));
            }
        }
    }

    public void focusBack()
    {
        BackExercises h = new BackExercises(0, null);

        for (int i = 0; i < program.size(); i++)
        {
            boolean flag = false;
            for (int j = 0; j < program.get(i).size(); j++)
            {
                if (program.get(i).get(j).getClass() == h.getClass())
                {
                    flag = true;
                }
            }
            if (flag)
            {
                this.program.get(i).add(Tester.getAvailableBackExercise(strength, program.get(i)));
            }
        }
    }

    public void focusLeg()
    {
        LegExercises h = new LegExercises(0, null);

        for (int i = 0; i < program.size(); i++)
        {
            boolean flag = false;
            for (int j = 0; j < program.get(i).size(); j++)
            {
                if (program.get(i).get(j).getClass() == h.getClass())
                {
                    flag = true;
                }
            }
            if (flag)
            {
                this.program.get(i).add(Tester.getAvailableBacakExercise(strength, program.get(i)));
            }
        }
    }

    public void focusArm()
    {
        GogusHareketi h = new GogusHareketi(0, null);

        for (int i = 0; i < program.size(); i++)
        {
            boolean flag = false;
            for (int j = 0; j < program.get(i).size(); j++)
            {
                if (program.get(i).get(j).getClass() == h.getClass())
                {
                    flag = true;
                }
            }
            if (flag)
            {
                Tester.getAvailableChestExercise(strength, program.get(i));
            }
        }

    }*/
}
