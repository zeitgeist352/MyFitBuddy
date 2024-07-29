package com.exercises;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myfitbuddy.R;
import com.algorithm.Tester;
import com.myfitbuddy.databinding.ExerciseListActivityBaseModelBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseEditAdapter extends RecyclerView.Adapter<ExerciseEditAdapter.ExerciseViewHolder>{

    private Tester t;
    private ArrayList<ExerciseModel> exerciseList;
    private Context context;
    private String day;

    public ExerciseEditAdapter(ArrayList<ExerciseModel> exerciseList, Context context, String day) {
        this.exerciseList = exerciseList;
        this.context = context;
        this.day = day;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExerciseListActivityBaseModelBinding binding = ExerciseListActivityBaseModelBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ExerciseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {

        t = new Tester();

        holder.binding.textExerciseName.setText(exerciseList.get(position).getName());
        holder.binding.textExerciseSetReps.setText("3x12");
        //photos
        if (exerciseList.get(position).getName().equalsIgnoreCase("Squat")){
            holder.binding.imageExercise.setImageResource(R.drawable.bodyweightsquat);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("Dumbbell Squat")) {
            holder.binding.imageExercise.setImageResource(R.drawable.squat);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("Leg Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.legpressnobg);
        }
        else if (exerciseList.get(position).getName().contains("Bulgarian Split Squat")){
            holder.binding.imageExercise.setImageResource(R.drawable.bulgariansplit);
        }
        else if (exerciseList.get(position).getName().contains("Jump Squat")){
            holder.binding.imageExercise.setImageResource(R.drawable.jumpsquat);
        }
        else if (exerciseList.get(position).getName().contains("Heck Squat")){
            holder.binding.imageExercise.setImageResource(R.drawable.hacksquat);
        }
        else if (exerciseList.get(position).getName().equalsIgnoreCase("Leg Curl")) {
            holder.binding.imageExercise.setImageResource(R.drawable.seatedlegcurl);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("calf raise")) {
            holder.binding.imageExercise.setImageResource(R.drawable.calfraisenobg);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("leg extension")) {
            holder.binding.imageExercise.setImageResource(R.drawable.legextension);
        } 
        else if (exerciseList.get(position).getName().equalsIgnoreCase("lunges")){
            holder.binding.imageExercise.setImageResource(R.drawable.lunge);
        }
        /*
        else if (Tester.isLegExercise(exerciseList.get(position).getName()))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.runningnobg);
        }

         */
        else if (exerciseList.get(position).getName().contains("Deadlift"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.barbelldeadlift);
        }
        else if (exerciseList.get(position).getName().contains("Rowing"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.rowingmachine);
        }
        else if (exerciseList.get(position).getName().equalsIgnoreCase("Pull-up"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.pullup);
        }
        
        else if (Tester.isBackExercise(exerciseList.get(position).getName()))
        {
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

         
        else if (Tester.isBicepsExercise(exerciseList.get(position).getName()))
        {
            if (exerciseList.get(position).getName().equalsIgnoreCase("barbell curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.inclinedumbbelcurl);
            }
            else if (exerciseList.get(position).getName().equalsIgnoreCase("dumbbell curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.dumbbellcurl);
            }
            else if(exerciseList.get(position).getName().equalsIgnoreCase("preacher curl") )
            {
                holder.binding.imageExercise.setImageResource(R.drawable.preachercurl);
            }
            else if(exerciseList.get(position).getName().equalsIgnoreCase("concentration curl"))
            {
                holder.binding.imageExercise.setImageResource(R.drawable.concentrationcurl);
            }
            else if(exerciseList.get(position).getName().equalsIgnoreCase("cable curl") )
            {
                holder.binding.imageExercise.setImageResource(R.drawable.cablecurl);
            }
            else if(exerciseList.get(position).getName().equalsIgnoreCase("spider curl"))
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
        else if (Tester.isTricepsExercise(exerciseList.get(position).getName()))
        {
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
        else if( exerciseList.get(position).getName().contains("Jumping Jacks"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.jumpingjacks);
        }
        else if(exerciseList.get(position).getName().contains("Plyometric"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.plyometric);
        }
        else if (exerciseList.get(position).getName().contains("Box Jumps")) {
            holder.binding.imageExercise.setImageResource(R.drawable.boxjumps);
        } else if (exerciseList.get(position).getName().contains("Cable Row")) {
            holder.binding.imageExercise.setImageResource(R.drawable.seatedvbarcablerow);
        } else if (exerciseList.get(position).getName().contains("Row"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.barbellrow);
        }
        else if (exerciseList.get(position).getName().contains("Running (High Tempo)") ||
                exerciseList.get(position).getName().contains("Running (Low Tempo)") ||
                exerciseList.get(position).getName().contains("Running") ||
                exerciseList.get(position).getName().contains("Trail Running") ||
                exerciseList.get(position).getName().contains("Sprinting"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.runningnobg);
        }
        else if(exerciseList.get(position).getName().contains("HIIT"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.hiit);
        }
        else if (exerciseList.get(position).getName().contains("Climbing"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.stairnobg);
        }
        else if (exerciseList.get(position).getName().contains("Cycling"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.cyclenbg);
        }
        else if (exerciseList.get(position).getName().contains("Circuit Training"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.circuittraining);
        }
        else if (exerciseList.get(position).getName().contains("Elliptical"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.ellipticalng);
        }
        else if (exerciseList.get(position).getName().contains("Burpees"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.burpeeng);
        }
        else if (exerciseList.get(position).getName().contains("Climbing"))
        {

            holder.binding.imageExercise.setImageResource(R.drawable.stairnobg);
        } else if (exerciseList.get(position).getName().contains("Mountain Climbers")) {
            holder.binding.imageExercise.setImageResource(R.drawable.mountainclimber);

        } else if (exerciseList.get(position).getName().contains("Upright Row")) {
            holder.binding.imageExercise.setImageResource(R.drawable.uprightrow);
        }
        else if (exerciseList.get(position).getName().contains("Push Press")) {
            holder.binding.imageExercise.setImageResource(R.drawable.pushpress);

        } else if (exerciseList.get(position).getName().contains("High Pulls")) {
            holder.binding.imageExercise.setImageResource(R.drawable.highpulls);

        } else if (exerciseList.get(position).getName().contains("Face Pull")){
            holder.binding.imageExercise.setImageResource(R.drawable.facepull);
        } else if (exerciseList.get(position).getName().contains("Shrugs")) {
            holder.binding.imageExercise.setImageResource(R.drawable.shrugs);
        }
        else if (exerciseList.get(position).getName().contains("Machine Shoulder Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.machineshoulder);
        } else if (exerciseList.get(position).getName().contains("Seated Dumbbell Press")) {
            holder.binding.imageExercise.setImageResource(R.drawable.shoulderpress);

        } else if (exerciseList.get(position).getName().contains("Military Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.militarypress);
        }
        else if (exerciseList.get(position).getName().contains("Arnold Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.arnoldpress);
        }
        else if (exerciseList.get(position).getName().contains("Lateral Raise")){
            holder.binding.imageExercise.setImageResource(R.drawable.latraise);
        }
        else if (exerciseList.get(position).getName().contains("Front Raise")){
            holder.binding.imageExercise.setImageResource(R.drawable.frontraise);
        }
        else if (exerciseList.get(position).getName().contains("Cable Lateral Raise")){
            holder.binding.imageExercise.setImageResource(R.drawable.cablelatraise);
        }
        else if (exerciseList.get(position).getName().contains("Barbell Front Raise")){
            holder.binding.imageExercise.setImageResource(R.drawable.barbellfrontraise);
        }
        else if (exerciseList.get(position).getName().contains("Bent Over Lateral Raise")){
            holder.binding.imageExercise.setImageResource(R.drawable.bentoverlatraise);
        }
        else if (exerciseList.get(position).getName().contains("Lateral (In machine)")) {
            holder.binding.imageExercise.setImageResource(R.drawable.latraise);
        }
        else if ( exerciseList.get(position).getName().contains("Dumbbell Fly")){
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellfly);
        }
        else if (exerciseList.get(position).getName().contains("Reverse Fly")){
            holder.binding.imageExercise.setImageResource(R.drawable.reversefly);
        }
        else if (exerciseList.get(position).getName().contains("Decline Dumbbell Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.declinedumbbellpress);
        }
        else if (exerciseList.get(position).getName().contains("Incline Dumbbell Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.inclinedumbbellpress);
        }
        else if (exerciseList.get(position).getName().contains("Dumbbell Press"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellpress);
        }
        else if (exerciseList.get(position).getName().contains("Incline Bench Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.inclinebenchpress);
        }
        else if (exerciseList.get(position).getName().contains("Decline Bench Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.declinebenchpress);
        }
        else if (exerciseList.get(position).getName().contains("Bench Press")){
            holder.binding.imageExercise.setImageResource(R.drawable.barbellbenchpress);
        }
        else if (exerciseList.get(position).getName().contains("Dips")){
            holder.binding.imageExercise.setImageResource(R.drawable.benchdips);
        }
        else if (exerciseList.get(position).getName().contains("Push-ups")){
            holder.binding.imageExercise.setImageResource(R.drawable.pushups);
        }
        else if (exerciseList.get(position).getName().contains("Cable Fly")){
            holder.binding.imageExercise.setImageResource(R.drawable.cablefly);
        }
        else if (exerciseList.get(position).getName().contains("Machine Fly")){
            holder.binding.imageExercise.setImageResource(R.drawable.machinefly);
        }
        else if (exerciseList.get(position).getName().contains("Pec Deck"))
        {
            holder.binding.imageExercise.setImageResource(R.drawable.pecdeck);
        }
        else if (exerciseList.get(position).getName().contains("Chest Squeeze")){
            holder.binding.imageExercise.setImageResource(R.drawable.chestsqueeze);
        }
        else if (exerciseList.get(position).getName().contains("Chest Press Machine")){
            holder.binding.imageExercise.setImageResource(R.drawable.chestpressmachine);
        }
        else
        {
            holder.binding.imageExercise.setImageResource(R.drawable.dumbbellfly);
        }
        holder.binding.imageEdit.setOnClickListener(view -> {
            showEditDialog(position);
        });
    }

    private void showEditDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select an Exercise");

        Tester tester = new Tester();
        // Example list of exercises. This should be dynamically loaded based on the muscle group or other criteria
        String[] exercises = Tester.returnAssociatedList(exerciseList.get(position).getName()).toArray(new String[0]);

        builder.setItems(exercises, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the exercise at the given position
                String selectedExercise = exercises[which];
                exerciseList.get(position).setName(selectedExercise);
                notifyItemChanged(position);

                // Update the exercise in the database
                updateExerciseInArray(day, position, selectedExercise);

            }
        });

        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void updateExerciseInArray(String day, int exerciseIndex, String newExerciseName) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Log.d(TAG, "No user logged in");
            return; // Early return if the user is not logged in
        }

        DocumentReference docRef = FirebaseFirestore.getInstance()
                .collection("Users")
                .document(currentUser.getUid());

        // Fetch the current array
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> data = (Map<String, Object>) documentSnapshot.getData();
                if (data != null && data.containsKey("program")) {
                    Map<String, Object> program = (Map<String, Object>) data.get("program");
                    if (program.containsKey(day)) {
                        List<String> exercises = new ArrayList<>((List<String>) program.get(day));
                        if (exerciseIndex >= 0 && exerciseIndex < exercises.size()) {
                            exercises.set(exerciseIndex, newExerciseName);
                            // Update the entire array for the day
                            docRef.update("program." + day, exercises)
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Exercise updated successfully in " + day + " at index " + exerciseIndex))
                                    .addOnFailureListener(e -> Log.w(TAG, "Error updating exercise at " + day + " index " + exerciseIndex, e));
                        } else {
                            Log.d(TAG, "Invalid exercise index");
                        }
                    } else {
                        Log.d(TAG, day + " does not exist in the program");
                    }
                } else {
                    Log.d(TAG, "Program data is missing in user document");
                }
            } else {
                Log.d(TAG, "No user document exists for UID: " + currentUser.getUid());
            }
        }).addOnFailureListener(e -> Log.w(TAG, "Error retrieving user document", e));
    }


    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{
        private ExerciseListActivityBaseModelBinding binding;
        public ExerciseViewHolder(ExerciseListActivityBaseModelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
