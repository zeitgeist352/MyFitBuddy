package com.exercises;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitbuddy.R;
import com.algorithm.Tester;
import com.myfitbuddy.databinding.ExerciseListBaseModelBinding;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{

    private ArrayList<ExerciseModel> exerciseList;
    private Tester t;

    public ExerciseAdapter(ArrayList<ExerciseModel> exerciseList) {
        this.exerciseList = exerciseList;

    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseListBaseModelBinding binding = ExerciseListBaseModelBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        t = new Tester();
        holder.binding.textExerciseName.setText(exerciseList.get(position).getName());
        holder.binding.textExerciseSetReps.setText("3x12");

        if (exerciseList.get(position).getName().equalsIgnoreCase("squat") ||
                exerciseList.get(position).getName().equalsIgnoreCase("dumbell squat") ||
                exerciseList.get(position).getName().equalsIgnoreCase("bulgarian split squat") ||
                exerciseList.get(position).getName().equalsIgnoreCase("hack squat") ||
                exerciseList.get(position).getName().equalsIgnoreCase("sumo squat")) {
            holder.binding.imageExercise.setImageResource(R.drawable.squat);
        } else if (exerciseList.get(position).getName().equalsIgnoreCase("leg press")) {
            holder.binding.imageExercise.setImageResource(R.drawable.legextension);
        } else if (exerciseList.get(position).getName().equalsIgnoreCase("leg curl") ||
                exerciseList.get(position).getName().equalsIgnoreCase("leg extension") ||
                exerciseList.get(position).getName().equalsIgnoreCase("calf raise") ||
                exerciseList.get(position).getName().equalsIgnoreCase("lunges") ||
                exerciseList.get(position).getName().equalsIgnoreCase("good mornings")) {
            holder.binding.imageExercise.setImageResource(R.drawable.leg);
        } else if (Tester.isLegExercise(exerciseList.get(position).getName())) {
            holder.binding.imageExercise.setImageResource(R.drawable.jumpsquat);
        } else if (exerciseList.get(position).getName().contains("Deadlift")) {
            holder.binding.imageExercise.setImageResource(R.drawable.barbellrow);
        } else if (exerciseList.get(position).getName().contains("Row")) {
            holder.binding.imageExercise.setImageResource(R.drawable.seatedvbarcablerow);
        } else if (exerciseList.get(position).getName().equalsIgnoreCase("Pull-up")) {
            holder.binding.imageExercise.setImageResource(R.drawable.pullup);
        } else if (Tester.isBackExercise(exerciseList.get(position).getName())) {
            holder.binding.imageExercise.setImageResource(R.drawable.back__2_);
        } else if (Tester.isBicepsExercise(exerciseList.get(position).getName())) {
            if (exerciseList.get(position).getName().equalsIgnoreCase("barbell curl") ||
                    exerciseList.get(position).getName().equalsIgnoreCase("dumbbell curl") ||
                    exerciseList.get(position).getName().equalsIgnoreCase("preacher curl") ||
                    exerciseList.get(position).getName().equalsIgnoreCase("concentration curl") ||
                    exerciseList.get(position).getName().equalsIgnoreCase("cable curl") ||
                    exerciseList.get(position).getName().equalsIgnoreCase("spider curl")) {
                holder.binding.imageExercise.setImageResource(R.drawable.inclinedumbbelcurl);
            } else {
                holder.binding.imageExercise.setImageResource(R.drawable.hammercurl);
            }
        } else if (Tester.isTricepsExercise(exerciseList.get(position).getName())) {
            if (exerciseList.get(position).getName().contains("Dip")) {
                holder.binding.imageExercise.setImageResource(R.drawable.benchdips);
            } else if (exerciseList.get(position).getName().contains("Pushdown") || exerciseList.get(position).getName().contains("Extension")) {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepspressdown);
            } else if (exerciseList.get(position).getName().contains("Kickback")) {
                holder.binding.imageExercise.setImageResource(R.drawable.kickback);
            } else {
                holder.binding.imageExercise.setImageResource(R.drawable.triceps);
            }
        } else if (exerciseList.get(position).getName().contains("Swimming")) {
            holder.binding.imageExercise.setImageResource(R.drawable.swimming);
        } else if (exerciseList.get(position).getName().contains("Jumping Rope") ||
                exerciseList.get(position).getName().contains("Box Jumps") ||
                exerciseList.get(position).getName().contains("Jumping Jacks") ||
                exerciseList.get(position).getName().contains("Jump Squats") ||
                exerciseList.get(position).getName().contains("Plyometric")) {
            holder.binding.imageExercise.setImageResource(R.drawable.jumping_rope);
        } else if (exerciseList.get(position).getName().contains("Rowing")) {
            holder.binding.imageExercise.setImageResource(R.drawable.seatedvbarcablerow);
        } else if (exerciseList.get(position).getName().contains("Running") ||
                exerciseList.get(position).getName().contains("HIIT") ||
                exerciseList.get(position).getName().contains("Sprinting")) {
            holder.binding.imageExercise.setImageResource(R.drawable.running);
        } else if (exerciseList.get(position).getName().contains("Climbing") ||
                (exerciseList.get(position).getName().contains("Mountain Climbers"))) {
            holder.binding.imageExercise.setImageResource(R.drawable.stair_climbing);
        } else if (exerciseList.get(position).getName().contains("Cycling") ||
                exerciseList.get(position).getName().contains("Circuit Training")) {
            holder.binding.imageExercise.setImageResource(R.drawable.cycling);
        } else if (exerciseList.get(position).getName().contains("Elliptical")) {
            holder.binding.imageExercise.setImageResource(R.drawable.elliptical);
        } else if (exerciseList.get(position).getName().contains("Burpees")) {
            holder.binding.imageExercise.setImageResource(R.drawable.burpee);
        } else if (exerciseList.get(position).getName().contains("Military Press") ||
                exerciseList.get(position).getName().contains("Arnold Press") ||
                exerciseList.get(position).getName().contains("Lateral Raise") ||
                exerciseList.get(position).getName().contains("Front Raise") ||
                exerciseList.get(position).getName().contains("Reverse Fly") ||
                exerciseList.get(position).getName().contains("Shrugs") ||
                exerciseList.get(position).getName().contains("Upright Row") ||
                exerciseList.get(position).getName().contains("Face Pull") ||
                exerciseList.get(position).getName().contains("Lateral (In machine)") ||
                exerciseList.get(position).getName().contains("Seated Dumbbell Press") ||
                exerciseList.get(position).getName().contains("High Pulls") ||
                exerciseList.get(position).getName().contains("Push Press") ||
                exerciseList.get(position).getName().contains("Dumbbell Pullover") ||
                exerciseList.get(position).getName().contains("Cable Lateral Raise") ||
                exerciseList.get(position).getName().contains("Bent Over Lateral Raise") ||
                exerciseList.get(position).getName().contains("Barbell Front Raise") ||
                exerciseList.get(position).getName().contains("Machine Shoulder Press")) {
            holder.binding.imageExercise.setImageResource(R.drawable.barbelldeadlift);
        } else if (exerciseList.get(position).getName().contains("Bench Press") ||
                exerciseList.get(position).getName().contains("Incline Bench Press") ||
                exerciseList.get(position).getName().contains("Decline Bench Press") ||
                exerciseList.get(position).getName().contains("Dumbbell Press") ||
                exerciseList.get(position).getName().contains("Incline Dumbbell Press") ||
                exerciseList.get(position).getName().contains("Decline Dumbbell Press") ||
                exerciseList.get(position).getName().contains("Cable Fly") ||
                exerciseList.get(position).getName().contains("Machine Fly") ||
                exerciseList.get(position).getName().contains("Dumbbell Fly") ||
                exerciseList.get(position).getName().contains("Dips")) {
            holder.binding.imageExercise.setImageResource(R.drawable.barbellbenchpress);
        } else if (exerciseList.get(position).getName().contains("Push-up")) {
            holder.binding.imageExercise.setImageResource(R.drawable.pushups);
        } else if (exerciseList.get(position).getName().contains("Pec Deck") ||
                exerciseList.get(position).getName().contains("Chest Press Machine") ||
                exerciseList.get(position).getName().contains("Chest Squeeze")) {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellfly);
        } else if (exerciseList.get(position).getName().contains("Climbing") ||
                (exerciseList.get(position).getName().contains("Mountain Climbers"))) {
            holder.binding.imageExercise.setImageResource(R.drawable.stair_climbing);
        } else {
            holder.binding.imageExercise.setImageResource(R.drawable.exercise);
        }


    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{
        private ExerciseListBaseModelBinding binding;
        public ExerciseViewHolder(ExerciseListBaseModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
