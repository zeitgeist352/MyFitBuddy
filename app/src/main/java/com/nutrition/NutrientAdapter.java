package com.nutrition;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myfitbuddy.R;

import java.util.List;

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.NutrientViewHolder> {

    private List<Nutrient> nutrientList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Nutrient nutrient);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public NutrientAdapter(List<Nutrient> nutrientList) {
        this.nutrientList = nutrientList;
    }

    @NonNull
    @Override
    public NutrientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrient_list_item, parent, false);
        return new NutrientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutrientViewHolder holder, int position) {
        Nutrient nutrient = nutrientList.get(position);
        holder.bind(nutrient);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(nutrient);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nutrientList.size();
    }

    public static class NutrientViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewCalories;
        private TextView textViewProtein;
        private TextView textViewCarbs;
        private TextView textViewFat;

        public NutrientViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_nutrient_name);
            textViewCalories = itemView.findViewById(R.id.textView_nutrient_calories);
            textViewProtein = itemView.findViewById(R.id.textView_nutrient_protein);
            textViewCarbs = itemView.findViewById(R.id.textView_nutrient_carbs);
            textViewFat = itemView.findViewById(R.id.textView_nutrient_fat);
        }

        public void bind(Nutrient nutrient) {
            textViewName.setText(nutrient.getName());
            textViewCalories.setText("Calories: " + nutrient.getCalories() + " kcal");
            textViewProtein.setText("Protein: " + nutrient.getProtein() + " g");
            textViewCarbs.setText("Carbs: " + nutrient.getCarbs() + " g");
            textViewFat.setText("Fat: " + nutrient.getFat() + " g");
        }
    }
}