

public abstract class Exercises implements Serializable{
    
    private double difficulty;
    private String name;

    public Exercises(double difficulty, String name) {
        this.difficulty = difficulty;
        this.name = name;
    }
  
    public double getDifficulty() {
        return this.difficulty;
    }

    public String getName() {
        return this.name;
    }

    public String toString()
    {
        return this.getClass().toString() + " Name: " + this.name + " Difficulty: " + this.difficulty + "\n";
    }
}


    

