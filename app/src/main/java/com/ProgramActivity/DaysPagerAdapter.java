package com.ProgramActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.exercises.ExerciseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaysPagerAdapter extends FragmentStateAdapter {

    //defining the variables
    private final List<String> daysList;
    private final Map<String, List<ExerciseModel>> exercisesByDay;

    //constructor for dayspageradapter and initialize the dayList and exercisesByDay
    public DaysPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> daysList, Map<String, List<ExerciseModel>> exercisesByDay) {
        super(fragmentManager, lifecycle);
        this.daysList = daysList;
        this.exercisesByDay = exercisesByDay;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<ExerciseModel> exercises = exercisesByDay.getOrDefault(daysList.get(position), new ArrayList<>());
        return DaysFragment.newInstance(exercises, daysList.get(position));
    }

    @Override
    //it returns prefered days count
    public int getItemCount() {
        return daysList.size();
    }
}
