package com.algorithm;

import java.io.Serializable;

public abstract class Exercises implements Comparable<Exercises>, Serializable{
    
    private double dif;
    private String name;

    public Exercises(double dif, String name) {
        this.dif = dif;
        this.name = name;
    }
  
    public double getDif() {
        return this.dif;
    }

    public String getIsim() {
        return this.name;
    }

    public String toString()
    {
        return this.getClass().toString() + " name: " + this.name + " dif: " + this.dif + "\n";
    }

    @Override
    public int compareTo(Exercises other) {
        
        if(this.dif > other.dif)
        {
            return -1;
        }
        else if(this.dif < other.dif)
        {
            return +1;
        }
        else 
        {
            return 0;
        }
    }

    public String getName()
    {
        return this.name;
    }
}
