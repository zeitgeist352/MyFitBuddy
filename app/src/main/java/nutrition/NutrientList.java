import java.util.ArrayList;

public class NutrientList{

    private <Nutrient> nutrients;
    private double totalProteins;
    private double totalCarbs;
    private double totalFat;
    private double totalCalories;

    public NutrientList(<Nutrient>nutrients)
    {
        this.nutrients = nutrients;
    }

    //getters
    public double getTotalProteins()
    {   
        double totalProtein = 0;
        for(int i = 0; i < this.nutrients.size(); i++)
        {   
            double proteinAmount = this.nutrients.get(i).getProteins();
            double grams = this.nutrients.get(i).getGrams();

            totalProtein += proteinAmount * grams / 100;
        }

        return totalProtein;
    }

    public double getTotalCarbs()
    {   
        double totalCarbs = 0;
        for(int i = 0; i < this.nutrients.size(); i++)
        {   
            double CarbsAmount = this.nutrients.get(i).getCarbs();
            double grams = this.nutrients.get(i).getGrams();

            totalCarbs += CarbsAmount * grams / 100;
        }

        return totalCarbs;
    }

    public double getTotalFats()
    {   
        double totalFats = 0;
        for(int i = 0; i < this.nutrients.size(); i++)
        {   
            double fatAmount = this.nutrients.get(i).getFats();
            double grams = this.nutrients.get(i).getGrams();

            totalFats += fatAmount * grams / 100;
        }

        return totalFats;
    }

    public double getTotalCalories()
    {   
        double totalCalories = 0;
        for(int i = 0; i < this.nutrients.size(); i++)
        {   
            double calories = this.nutrients.get(i).getCalories();
            double grams = this.nutrients.get(i).getGrams();

            totalCalories += calories * grams / 100;
        }

        return totalCalories;
    }

    //additions
    public void addNutrient(Nutrient nutrient)
    {
        this.nutritionList.add(nutrient);
    }

    public void removeNutrient(Nutrient nutrient)
    {
        this.nutritionList.remove(nutrient);
    }

    public String toString()
    {
        String nutritionList = "";

        for(int i = 0; i < this.nutrients.size();  i++)
        {
            nutritionList = this.nutrients.get(i).toString() + "\n";
        }

        return nutritionList;
    }

}