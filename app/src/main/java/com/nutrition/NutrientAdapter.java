
package com.nutrition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitbuddy.R;

import java.util.ArrayList;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.NutrientViewHolder> {

    //defining variables
    private ArrayList<Nutrient> nutrientList;
    private OnItemClickListener onItemClickListener;

    public NutrientAdapter(ArrayList<Nutrient> nutrientList) {
        this.nutrientList = nutrientList;
    }

    //click listener for deleting nutrient
    public interface OnItemClickListener {
        void onItemClick(Nutrient nutrient);
        void onDeleteClick(Nutrient nutrient);
    }

    //it sets the item click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public NutrientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrient_list_item, parent, false);
        return new NutrientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientViewHolder holder, int position) {
        Nutrient currentNutrient = nutrientList.get(position);
        holder.textViewName.setText(currentNutrient.getName());
        holder.textViewCalories.setText("Calories: " + currentNutrient.getCalories() + " kcal");
        holder.textViewProtein.setText("Protein: " + currentNutrient.getProtein() + " g");
        holder.textViewCarbs.setText("Carbs: " + currentNutrient.getCarbs() + " g");
        holder.textViewFat.setText("Fat: " + currentNutrient.getFat() + " g");
    }

    @Override
    //it returns the nutrients' count
    public int getItemCount() {
        return nutrientList.size();
    }

    class NutrientViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewCalories, textViewProtein, textViewCarbs, textViewFat;
        ImageButton buttonRemoveNutrient;

        public NutrientViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_nutrient_name);
            textViewCalories = itemView.findViewById(R.id.textView_nutrient_calories);
            textViewProtein = itemView.findViewById(R.id.textView_nutrient_protein);
            textViewCarbs = itemView.findViewById(R.id.textView_nutrient_carbs);
            textViewFat = itemView.findViewById(R.id.textView_nutrient_fat);
            buttonRemoveNutrient = itemView.findViewById(R.id.button_remove_nutrient);

            buttonRemoveNutrient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onDeleteClick(nutrientList.get(position));
                        }
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(nutrientList.get(position));
                        }
                    }
                }
            });
        }
    }
}