package com.example.trainingconstructor.Views.RecyclerViews.AddPointTrainingRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trainingconstructor.R;


public class AddPointTrainingViewHolder extends RecyclerView.ViewHolder   {

        private final TextView nameExercise;

        private AddPointTrainingViewHolder(View itemView) {
            super(itemView);
            nameExercise = itemView.findViewById(R.id.name_exercise);
        }

        public void bind(String text) {
            nameExercise.setText(text);
        }

        static AddPointTrainingViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.add_point_training_item, parent, false);
            return new AddPointTrainingViewHolder(view);
        }
    }