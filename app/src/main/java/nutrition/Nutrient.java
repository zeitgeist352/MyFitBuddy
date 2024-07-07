
public class Nutrient {

    private String name;
    private double proteinsPer100Gram;
    private double carbsPer100Gram;
    private double fatsPer100Gram;
    private double caloriesPer100Gram;

    public Nutrient(String name , double proteins, double carbs, double fat, double calories)
    {
        this.name = name;
        this.proteinsPer100Gram = proteins;
        this.carbsPer100Gram = carbs;
        this.fatsPer100Gram = fat;
        this.caloriesPer100Gram = calories;
    }

    public String getName()
    {
        return this.name;
    }

    public double getProteins(double grams)
    {
        return this.proteinsPer100Gram * grams / 100;
    }

    public double getCarbs(double grams)
    {
        return this.carbsPer100Gram * grams / 100;
    }

    public double getFats(double grams)
    {
        return this.fatsPer100Gram * grams / 100;
    }

    public double getCalories(double grams)
    {
        return this.CaloriesPer100Gram * grams / 100;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setProteins(double proteins)
    {
        this.proteinsPer100Gram = proteins;
    }

    public void setCarbs(double carbs)
    {
        this.carbsPer100Gram = carbs;
    }
    
    public void setFats(double fats)
    {
        this.fatsPer100Gram = fats;
    }

    public void setCalories(double calories)
    {
        this.caloriesPer100Gram = calories;
    }
}
