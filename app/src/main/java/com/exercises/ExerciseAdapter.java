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
                exerciseList.get(position).getName().equalsIgnoreCase("bulgarian split squat")) {
            holder.binding.imageExercise.setImageResource(R.drawable.bodyweightsquat);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("leg press")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.legpressnobg);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("dumbbell squat") ||
                exerciseList.get(position).getName().equalsIgnoreCase("hack squat")) {
            holder.binding.imageExercise.setImageResource(R.drawable.squat);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("leg curl")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.legcurlnobg);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("leg extension")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.legextension);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("calf raise")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.calfraisenobg);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("lunges")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.lunge);
        } 
        else if (exerciseList.get(position).getName().contains("Running (High Tempo") ||
                exerciseList.get(position).getName().contains("Running (Low Tempo")||
                exerciseList.get(position).getName().contains("Trail Running")||
                exerciseList.get(position).getName().contains("Running")||
                exerciseList.get(position).getName().contains("Sprinting")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.runningnobg);
        }
        else if (Tester.isLegExercise(exerciseList.get(position).getName())) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.legnobg);
        } 
        else if (exerciseList.get(position).getName().contains("Deadlift")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.barbelldeadlift);
        } 
        else if (exerciseList.get(position).getName().contains("Rowing")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.rowingmachine);
        } 
        
        else if (Tester.isBackExercise(exerciseList.get(position).getName())) {
             if(exerciseList.get(position).getName().contains("Dumbbell Row"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.dumbbellrow);
            }
            else if(exerciseList.get(position).getName().contains("Inverted Row"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.invertedrow);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Pull-up")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.pullup);
            } 
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Barbell Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.barbellrow);
            } 
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Lat Pull Down")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.latpulldownnobg);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Conventional Deadlift")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.conventionaldeadlift);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Cable Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.cablerow1);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Seated Cable Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.cablerow);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("T-Bar Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tbarrow);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Face Pulls")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.facepull);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Romanian Deadlift")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.romaniandeadlift);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Single Arm Dumbbell Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.singlearmdumbbellrow);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Bent Over Barbell Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.bentoverbarbellrow);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Machine Row")) 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.machinerow);
            }
            else 
            {
                holder.binding.imageExercise.setImageResource(R.drawable.backnobg);
            }
        } 
        else if (Tester.isBicepsExercise(exerciseList.get(position).getName())) {
            if (exerciseList.get(position).getName().equalsIgnoreCase("barbell curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.barbellcurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("dumbbell curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.dumbbellcurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("preacher curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.preachercurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("concentration curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.concentrationcurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("cable curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.cablecurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("spider curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.spidercurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("hammer curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.hammercurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Reverse curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.reversecurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("alternating hammer curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.alternatinghammercurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Cable hammer curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.cablehammercurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("incline dumbbel curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.inclinedumbbelcurl);
            }
        } 
        else if (Tester.isTricepsExercise(exerciseList.get(position).getName())) {
            if (exerciseList.get(position).getName().equalsIgnoreCase("Skull Crusher"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.skullcrusher);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Bench Dip"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepsbenchdips);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Overhead Dumbbell Triceps Press"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.overheaddumbelltricepspress);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Cable Kickback"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepscablekickback);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Dips"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepsdips);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Overhead Triceps Extension"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.overheadtricepsextension);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Kickback"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.kickback);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Push Down"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepspressdown);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Triceps Rope Push down"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepsropepushdown);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Triceps Rope Overhead Extension"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.tricepsropeoverheadextension);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("Dumbbell Triceps Extension"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.dumbbelltricepsextension);
            }
             else {
                holder.binding.imageExercise.setImageResource(R.drawable.triceps);
            }
        }
        else if (exerciseList.get(position).getName().contains("Swimming"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.swimming);
        }
        else if (exerciseList.get(position).getName().contains("Jumping Rope"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.ropenobg);
        }
        else if (exerciseList.get(position).getName().contains("Box Jumps"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.boxjumps);
        }
        else if (exerciseList.get(position).getName().contains("Jumping Jacks"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.jumpingjacks);
        }
        else if (exerciseList.get(position).getName().contains("Jump Squats"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.jumpsquat);
        }
        else if (exerciseList.get(position).getName().contains("Plyometric"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.plyometric);
        }
        else if (exerciseList.get(position).getName().contains("Row")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.seatedvbarcablerow);
        }
        else if(exerciseList.get(position).getName().contains("HIIT"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.hiit);
        }
        else if (exerciseList.get(position).getName().contains("Climbing")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.stairnobg);
        }
        else if(exerciseList.get(position).getName().contains("Mountain Climbers"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.mountainclimber);
        }
        else if (exerciseList.get(position).getName().contains("Cycling")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.cyclenbg);
        } 
        else if (exerciseList.get(position).getName().contains("Circuit Training")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.circuttraining);
        }
        else if (exerciseList.get(position).getName().contains("Elliptical")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.ellipticalng);
        }
        else if (exerciseList.get(position).getName().contains("Burpees"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.burpeeng);
        }
        else if (exerciseList.get(position).getName().contains("Seated Dumbbell Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.shoulderpress);
        }
        else if(exerciseList.get(position).getName().contains("Dumbbell Pullover"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellpullover);
        }
        else if(exerciseList.get(position).getName().contains("Machine Shoulder Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.machineshoulder);
        }

        else if (exerciseList.get(position).getName().contains("Face Pull"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.facepull);
        }
        else if(exerciseList.get(position).getName().contains("High Pulls"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.highpulls);
        }
        else if(exerciseList.get(position).getName().contains("Upright Row"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.uprightrow);
        }
        else if(exerciseList.get(position).getName().contains("Push Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.pushpress);
        }
        else if (exerciseList.get(position).getName().contains("Military Press")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.militarypress);
        }
        else if (exerciseList.get(position).getName().contains("Arnold Press")) 
        {
            holder.binding.imageExercise.setImageResource(R.drawable.arnoldpress);

        }else if (exerciseList.get(position).getName().contains("Shrugs")) {
            holder.binding.imageExercise.setImageResource(R.drawable.shrugs);

        }else if (exerciseList.get(position).getName().contains("Lateral (In machine)")||
                exerciseList.get(position).getName().contains("Lateral Raise"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.latraise);
        }
        else if(exerciseList.get(position).getName().contains("Front Raise"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.frontraise);
        }
        else if(exerciseList.get(position).getName().contains("Cable Lateral Raise"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.cablelatraise);
        }
        else if(exerciseList.get(position).getName().contains("Barbell Front Raise"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.barbellfrontraise);
        }
        else if(exerciseList.get(position).getName().contains("Bent Over Lateral Raise"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.bentoverlatraise);
        }
        else if (exerciseList.get(position).getName().contains("Bench Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.barbellbenchpress);
        }
        else if( exerciseList.get(position).getName().contains("Incline Bench Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.inclinebenchpress);
        }
        else if(exerciseList.get(position).getName().contains("Decline Bench Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.declinebenchpress);
        }
        else if (exerciseList.get(position).getName().contains("Dumbbell Fly"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellfly);
        }
        else if( exerciseList.get(position).getName().contains("Reverse Fly"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.reversefly);
        }
        else if(exerciseList.get(position).getName().contains("Machine Fly"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.machinefly);
        }
        else if(exerciseList.get(position).getName().contains("Cable Fly"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.cablefly);
        }
        else if(exerciseList.get(position).getName().contains("Dips")){
            holder.binding.imageExercise.setImageResource(R.drawable.benchdips);
        } else if(exerciseList.get(position).getName().contains("Dumbbell Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellpress);
        }
        else if(exerciseList.get(position).getName().contains("Incline Dumbbell Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.inclinedumbbellpress);
        }
        else if(exerciseList.get(position).getName().contains("Decline Dumbbell Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.declinedumbbellpress);
        }

        else if (exerciseList.get(position).getName().contains("Push-up")) {
            holder.binding.imageExercise.setImageResource(R.drawable.pushups);
        } else if (exerciseList.get(position).getName().contains("Pec Deck"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.chestpress);
        }
        else if(exerciseList.get(position).getName().contains("Chest Press Machine"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.chestpressmachine);
        }
        else if(exerciseList.get(position).getName().contains("Chest Squeeze"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.chestsqueeze);
        }
        else if (exerciseList.get(position).getName().contains("Climbing"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.stairnobg);
        }
        else if(exerciseList.get(position).getName().contains("Mountain Climber"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.mountainclimber);
        }
        else
        {
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
