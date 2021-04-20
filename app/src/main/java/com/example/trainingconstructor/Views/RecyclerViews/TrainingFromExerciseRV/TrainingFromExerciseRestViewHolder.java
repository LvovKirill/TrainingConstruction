package com.example.trainingconstructor.Views.RecyclerViews.TrainingFromExerciseRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.R;


public class TrainingFromExerciseRestViewHolder extends RecyclerView.ViewHolder   {

    private final TextView timeRest;

    private TrainingFromExerciseRestViewHolder(View itemView) {
        super(itemView);
        timeRest = itemView.findViewById(R.id.timeRest);
    }

    public void bind(String time) {
        timeRest.setText(time);
    }

    static TrainingFromExerciseRestViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.training_from_exercise_rest_item, parent, false);
        return new TrainingFromExerciseRestViewHolder(view);
    }
}