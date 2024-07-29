package com.algorithm;

import java.util.ArrayList;

public class WorkoutPrograms {

    //defining ArrayList variables
    ArrayList<ArrayList<Exercises>>[] buildMusclePrograms;
    ArrayList<ArrayList<Exercises>> twoDsMuscleFocus;
    ArrayList<ArrayList<Exercises>> threeDsMuscleFocus;
    ArrayList<ArrayList<Exercises>> fourDsMuscleFocus;
    ArrayList<ArrayList<Exercises>> fiveDsMuscleFocus;

    ArrayList<ArrayList<Exercises>>[] cardioPrograms;
    ArrayList<ArrayList<Exercises>> twoDsCardioFocus;
    ArrayList<ArrayList<Exercises>> threeDsCardioFocus;
    ArrayList<ArrayList<Exercises>> fourDsCardioFocus;
    ArrayList<ArrayList<Exercises>> fiveDsCardioFocus;

    ArrayList<ArrayList<Exercises>>[] mixedPrograms;
    ArrayList<ArrayList<Exercises>> twoDaysMixed;
    ArrayList<ArrayList<Exercises>> threeDaysMixed;
    ArrayList<ArrayList<Exercises>> fourDaysMixed;
    ArrayList<ArrayList<Exercises>> fiveDaysMixed;

    //creating a constructor for starting the program generators for 2-3-4-5 days
    public WorkoutPrograms()
    {
        twoDaysBuildMuscleProgramGenerator();
        threeDaysBuildMuscleProgramGenerator();
        fourDaysBuildMuscleProgramGenerator();
        fiveDaysBuildMuscleProgramGenerator();

        buildMusclePrograms = new ArrayList[4];
        buildMusclePrograms[0] = twoDsMuscleFocus;
        buildMusclePrograms[1] = threeDsMuscleFocus;
        buildMusclePrograms[2] = fourDsMuscleFocus;
        buildMusclePrograms[3] = fiveDsMuscleFocus;

        twoDsCardioFocusGenerator();
        threeDsCardioFocusGenerator();
        fourDsCardioFocusGenerator();
        fiveDsCardioFocusGenerator();

        cardioPrograms = new ArrayList[4];
        cardioPrograms[0] = twoDsCardioFocus;
        cardioPrograms[1] = threeDsCardioFocus;
        cardioPrograms[2] = fourDsCardioFocus;
        cardioPrograms[3] = fiveDsCardioFocus;

        twoDaysBuildMixedProgramGenerator();
        threeDaysBuildMixedProgramGenerator();
        fourDaysBuildMixedProgramGenerator();
        fiveDaysBuildMixedProgramGenerator();

        mixedPrograms = new ArrayList[4];
        mixedPrograms[0] = twoDaysMixed;
        mixedPrograms[1] = threeDaysMixed;
        mixedPrograms[2] = fourDaysMixed;
        mixedPrograms[3] = fiveDaysMixed;
    }

    //A program generator for people who choose building muscle and 2 days program option
    private void twoDaysBuildMuscleProgramGenerator()
    {
        twoDsMuscleFocus = new ArrayList<>();
        twoDsMuscleFocus.add(new ArrayList<>());

        twoDsMuscleFocus.get(0).add(new BackExercises(0, null));
        twoDsMuscleFocus.get(0).add(new BicepsExercises(0, null));
        twoDsMuscleFocus.get(0).add(new ShoulderExercises(0, null));
        twoDsMuscleFocus.get(0).add(new LegExercises(0, null));

        twoDsMuscleFocus.add(new ArrayList<>());
        twoDsMuscleFocus.get(1).add(new ChestExercises(0, null));
        twoDsMuscleFocus.get(1).add(new TricepsExercises(0, null));
        twoDsMuscleFocus.get(1).add(new BackExercises(0, null));
        twoDsMuscleFocus.get(1).add(new LegExercises(0, null));
    }

    //A program generator for people who choose building muscle and 3 days program option
    private void threeDaysBuildMuscleProgramGenerator()
    {
        threeDsMuscleFocus = new ArrayList<>();
        threeDsMuscleFocus.add(new ArrayList<>());
        threeDsMuscleFocus.get(0).add(new LegExercises(0, null));
        threeDsMuscleFocus.get(0).add(new ChestExercises(0, null));
        threeDsMuscleFocus.get(0).add(new BackExercises(0, null));

        threeDsMuscleFocus.add(new ArrayList<>());
        threeDsMuscleFocus.get(1).add(new LegExercises(0, null));
        threeDsMuscleFocus.get(1).add(new ShoulderExercises(0, null));
        threeDsMuscleFocus.get(1).add(new BackExercises(0, null));
        threeDsMuscleFocus.get(1).add(new TricepsExercises(0, null));

        threeDsMuscleFocus.add(new ArrayList<>());
        threeDsMuscleFocus.get(2).add(new LegExercises(0, null));
        threeDsMuscleFocus.get(2).add(new ChestExercises(0, null));
        threeDsMuscleFocus.get(2).add(new BackExercises(0, null));
        threeDsMuscleFocus.get(2).add(new BicepsExercises(0, null));
    }

    //A program generator for people who choose building muscle and 4 days program option
    private void fourDaysBuildMuscleProgramGenerator()
    {
        fourDsMuscleFocus = new ArrayList<>();
        fourDsMuscleFocus.add(new ArrayList<>());
        fourDsMuscleFocus.get(0).add(new BackExercises(0, null));
        fourDsMuscleFocus.get(0).add(new BicepsExercises(0, null));
        fourDsMuscleFocus.get(0).add(new ShoulderExercises(0, null));
        fourDsMuscleFocus.get(0).add(new LegExercises(0, null));

        fourDsMuscleFocus.add(new ArrayList<>());
        fourDsMuscleFocus.get(1).add(new ChestExercises(0, null));
        fourDsMuscleFocus.get(1).add(new TricepsExercises(0, null));
        fourDsMuscleFocus.get(1).add(new BackExercises(0, null));
        fourDsMuscleFocus.get(1).add(new LegExercises(0, null));
    
        fourDsMuscleFocus.add(new ArrayList<>());
        fourDsMuscleFocus.get(2).add(new BackExercises(0, null));
        fourDsMuscleFocus.get(2).add(new BicepsExercises(0, null));
        fourDsMuscleFocus.get(2).add(new ShoulderExercises(0, null));
        fourDsMuscleFocus.get(2).add(new LegExercises(0, null));

        fourDsMuscleFocus.add(new ArrayList<>());
        fourDsMuscleFocus.get(3).add(new ChestExercises(0, null));
        fourDsMuscleFocus.get(3).add(new TricepsExercises(0, null));
        fourDsMuscleFocus.get(3).add(new BackExercises(0, null));
        fourDsMuscleFocus.get(3).add(new LegExercises(0, null));
    }

    //A program generator for people who choose building muscle and 5 days program option
    private void fiveDaysBuildMuscleProgramGenerator()
    {
        fiveDsMuscleFocus = new ArrayList<>();
        fiveDsMuscleFocus.add(new ArrayList<>());
        fiveDsMuscleFocus.get(0).add(new BackExercises(0, null));
        fiveDsMuscleFocus.get(0).add(new BackExercises(0, null));
        fiveDsMuscleFocus.get(0).add(new BicepsExercises(0, null));
        fiveDsMuscleFocus.get(0).add(new BicepsExercises(0, null));

        fiveDsMuscleFocus.add(new ArrayList<>());
        fiveDsMuscleFocus.get(1).add(new ChestExercises(0, null));
        fiveDsMuscleFocus.get(1).add(new ChestExercises(0, null));
        fiveDsMuscleFocus.get(1).add(new TricepsExercises(0, null));
        fiveDsMuscleFocus.get(1).add(new ShoulderExercises(0, null));
    
        fiveDsMuscleFocus.add(new ArrayList<>());
        fiveDsMuscleFocus.get(2).add(new LegExercises(0, null));
        fiveDsMuscleFocus.get(2).add(new LegExercises(0, null));
        fiveDsMuscleFocus.get(2).add(new BicepsExercises(0, null));

        fiveDsMuscleFocus.add(new ArrayList<>());
        fiveDsMuscleFocus.get(3).add(new BackExercises(0, null));
        fiveDsMuscleFocus.get(3).add(new ShoulderExercises(0, null));
        fiveDsMuscleFocus.get(3).add(new ChestExercises(0, null));

        fiveDsMuscleFocus.add(new ArrayList<>());
        fiveDsMuscleFocus.get(4).add(new LegExercises(0, null));
        fiveDsMuscleFocus.get(4).add(new LegExercises(0, null));
        fiveDsMuscleFocus.get(4).add(new TricepsExercises(0, null));
    }

    //A program generator for people who choose cardio exercises and 2 days program option
    private void twoDsCardioFocusGenerator()
    {
        twoDsCardioFocus = new ArrayList<>();

        twoDsCardioFocus.add(new ArrayList<>());
        twoDsCardioFocus.add(new ArrayList<>());

        cardioHelper(twoDsCardioFocus);
    }

    //A program generator for people who choose cardio exercises and 3 days program option
    private void threeDsCardioFocusGenerator()
    {
        threeDsCardioFocus = new ArrayList<>();

        threeDsCardioFocus.add(new ArrayList<>());
        threeDsCardioFocus.add(new ArrayList<>());
        threeDsCardioFocus.add(new ArrayList<>());

        cardioHelper(threeDsCardioFocus);
    }

    //A program generator for people who choose cardio exercises and 4 days program option
    private void fourDsCardioFocusGenerator()
    {
        fourDsCardioFocus = new ArrayList<>();

        fourDsCardioFocus.add(new ArrayList<>());
        fourDsCardioFocus.add(new ArrayList<>());
        fourDsCardioFocus.add(new ArrayList<>());
        fourDsCardioFocus.add(new ArrayList<>());

        cardioHelper(fourDsCardioFocus);
    }

    //A program generator for people who choose cardio exercises and 5 days program option
    private void fiveDsCardioFocusGenerator()
    {
        fiveDsCardioFocus = new ArrayList<>();

        fiveDsCardioFocus.add(new ArrayList<>());
        fiveDsCardioFocus.add(new ArrayList<>());
        fiveDsCardioFocus.add(new ArrayList<>());
        fiveDsCardioFocus.add(new ArrayList<>());
        fiveDsCardioFocus.add(new ArrayList<>());

        cardioHelper(fiveDsCardioFocus);
    }

    private void cardioHelper(ArrayList<ArrayList<Exercises>> exerciseList)
    {
        for (int i = 0; i < exerciseList.size(); i++)
        {
            exerciseList.get(i).add(new CardioExercises(0, null));
            exerciseList.get(i).add(new CardioExercises(0, null));
            exerciseList.get(i).add(new CardioExercises(0, null));
        }
    }

    //A program generator for people who choose mix program and 2 days program option
    private void twoDaysBuildMixedProgramGenerator() // Start of mixed programs
    {
        twoDaysMixed = new ArrayList<>();
        twoDaysMixed.add(new ArrayList<>());

        twoDaysMixed.get(0).add(new BackExercises(0, null));
        twoDaysMixed.get(0).add(new BicepsExercises(0, null));
        twoDaysMixed.get(0).add(new CardioExercises(0, null)); // half of the day cardio and half of it other muscle groups
        twoDaysMixed.get(0).add(new CardioExercises(0, null));

        twoDaysMixed.add(new ArrayList<>());
        twoDaysMixed.get(1).add(new ChestExercises(0, null));
        twoDaysMixed.get(1).add(new TricepsExercises(0, null));
        twoDaysMixed.get(1).add(new CardioExercises(0, null));
        twoDaysMixed.get(1).add(new CardioExercises(0, null));
    }

    //A program generator for people who choose mix program and 3 days program option
    private void threeDaysBuildMixedProgramGenerator()
    {
        threeDaysMixed = new ArrayList<>();
        threeDaysMixed.add(new ArrayList<>());
        threeDaysMixed.get(0).add(new BackExercises(0, null));
        threeDaysMixed.get(0).add(new ChestExercises(0, null));
        threeDaysMixed.get(0).add(new CardioExercises(0, null));
        threeDaysMixed.get(0).add(new CardioExercises(0, null));

        threeDaysMixed.add(new ArrayList<>());
        threeDaysMixed.get(1).add(new LegExercises(0, null));
        threeDaysMixed.get(1).add(new ShoulderExercises(0, null));
        threeDaysMixed.get(1).add(new CardioExercises(0, null));
        threeDaysMixed.get(1).add(new CardioExercises(0, null));

        threeDaysMixed.add(new ArrayList<>());
        threeDaysMixed.get(2).add(new LegExercises(0, null));
        threeDaysMixed.get(2).add(new ChestExercises(0, null));
        threeDaysMixed.get(2).add(new CardioExercises(0, null));
        threeDaysMixed.get(2).add(new CardioExercises(0, null));
    }

    //A program generator for people who choose mix program and 4 days program option
    private void fourDaysBuildMixedProgramGenerator()
    {
        fourDaysMixed = new ArrayList<>();
        fourDaysMixed.add(new ArrayList<>());
        fourDaysMixed.get(0).add(new BackExercises(0, null));
        fourDaysMixed.get(0).add(new BicepsExercises(0, null));
        fourDaysMixed.get(0).add(new CardioExercises(0, null));
        fourDaysMixed.get(0).add(new CardioExercises(0, null));

        fourDaysMixed.add(new ArrayList<>());
        fourDaysMixed.get(1).add(new ChestExercises(0, null));
        fourDaysMixed.get(1).add(new TricepsExercises(0, null));
        fourDaysMixed.get(1).add(new CardioExercises(0, null));
        fourDaysMixed.get(1).add(new CardioExercises(0, null));
    
        fourDaysMixed.add(new ArrayList<>());
        fourDaysMixed.get(2).add(new BackExercises(0, null));
        fourDaysMixed.get(2).add(new LegExercises(0, null));
        fourDaysMixed.get(2).add(new CardioExercises(0, null));
        fourDaysMixed.get(2).add(new CardioExercises(0, null));

        fourDaysMixed.add(new ArrayList<>());
        fourDaysMixed.get(3).add(new ChestExercises(0, null));
        fourDaysMixed.get(3).add(new TricepsExercises(0, null));
        fourDaysMixed.get(3).add(new CardioExercises(0, null));
        fourDaysMixed.get(3).add(new CardioExercises(0, null));
    }

    //A program generator for people who choose mix program and 5 days program option
    private void fiveDaysBuildMixedProgramGenerator()
    {
        fiveDaysMixed = new ArrayList<>();
        fiveDaysMixed.add(new ArrayList<>());
        fiveDaysMixed.get(0).add(new BackExercises(0, null));
        fiveDaysMixed.get(0).add(new BackExercises(0, null));
        fiveDaysMixed.get(0).add(new CardioExercises(0, null));
        fiveDaysMixed.get(0).add(new CardioExercises(0, null));

        fiveDaysMixed.add(new ArrayList<>());
        fiveDaysMixed.get(1).add(new ChestExercises(0, null));
        fiveDaysMixed.get(1).add(new LegExercises(0, null));
        fiveDaysMixed.get(1).add(new CardioExercises(0, null));
        fiveDaysMixed.get(1).add(new CardioExercises(0, null));
    
        fiveDaysMixed.add(new ArrayList<>());
        fiveDaysMixed.get(2).add(new TricepsExercises(0, null));
        fiveDaysMixed.get(2).add(new LegExercises(0, null));
        fiveDaysMixed.get(2).add(new CardioExercises(0, null));
        fiveDaysMixed.get(2).add(new CardioExercises(0, null));

        fiveDaysMixed.add(new ArrayList<>());
        fiveDaysMixed.get(3).add(new BackExercises(0, null));
        fiveDaysMixed.get(3).add(new ShoulderExercises(0, null));
        fiveDaysMixed.get(3).add(new CardioExercises(0, null));
        fiveDaysMixed.get(3).add(new CardioExercises(0, null));

        fiveDaysMixed.add(new ArrayList<>());
        fiveDaysMixed.get(4).add(new LegExercises(0, null));
        fiveDaysMixed.get(4).add(new TricepsExercises(0, null));
        fiveDaysMixed.get(4).add(new CardioExercises(0, null));
        fiveDaysMixed.get(4).add(new CardioExercises(0, null)); // End of mixed programs
    }

    //it returns the building muscle program
    public ArrayList<ArrayList<Exercises>>[] getBuildMusclePrograms()
    {
        return buildMusclePrograms;
    }

    //it returns the building cardio program
    public ArrayList<ArrayList<Exercises>>[] getCardioPrograms()
    {
        return  this.cardioPrograms;
    }

    //it returns the mixed program
    public ArrayList<ArrayList<Exercises>>[] getMixedPrograms()
    {
        return this.mixedPrograms;
    }
    
}
