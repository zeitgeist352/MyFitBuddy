package com.report;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myfitbuddy.R;
import com.report.DayProgress;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import com.myfitbuddy.R;


import java.util.List;

public class DayProgressAdapter extends RecyclerView.Adapter<DayProgressAdapter.DayViewHolder> {

    private final List<DayProgress> dayProgressList;

    public DayProgressAdapter(List<DayProgress> dayProgressList) {
        this.dayProgressList = dayProgressList;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_progress, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        DayProgress progress = dayProgressList.get(position);

        holder.dayText.setText(progress.getDay());
        holder.burnedText.setText("Burned: " + progress.getBurnedCalories() + " kcal");
        holder.consumedText.setText("Consumed: " + progress.getConsumedCalories() + " kcal");

        int burned = progress.getBurnedCalories();
        int consumed = progress.getConsumedCalories();

        int max = Math.max(burned, consumed);
        holder.burnedBar.setMax(max);
        holder.consumedBar.setMax(max);

        holder.burnedBar.setProgress(burned);
        holder.consumedBar.setProgress(consumed);
    }

    @Override
    public int getItemCount() {
        return dayProgressList.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, burnedText, consumedText;
        ProgressBar burnedBar, consumedBar;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.textDay);
            burnedText = itemView.findViewById(R.id.textBurned);
            consumedText = itemView.findViewById(R.id.textConsumed);
            burnedBar = itemView.findViewById(R.id.progressBurned);
            consumedBar = itemView.findViewById(R.id.progressConsumed);
        }
    }
}